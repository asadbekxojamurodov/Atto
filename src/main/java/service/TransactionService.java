package service;

import dto.CardDto;
import dto.ProfileDto;
import dto.TransactionDto;
import enums.TransactionType;
import repository.CardRepository;
import repository.TerminalRepository;
import repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;

public class TransactionService {
    private TransactionRepository transactionRepository = new TransactionRepository();
    TerminalRepository terminalRepository = new TerminalRepository();
    CardRepository cardRepository = new CardRepository();


    public void transactionList() {
        List<TransactionDto> allTransaction = transactionRepository.getAllTransaction();
        for (TransactionDto transaction : allTransaction) {
            System.out.println(transaction);
        }
    }

    public void companyCardBalance() {
        CardDto companyCardBalance = transactionRepository.getCompanyCardBalance();
        System.out.println(companyCardBalance);

    }


    public void createTransaction(Integer cardId, String cardNumber, String terminalCode, double balance, TransactionType type, String phone) {

        TransactionDto transaction = new TransactionDto();
        transaction.setCardId(cardId);
        transaction.setCardNumber(cardNumber);
        transaction.setTerminalCode(terminalCode);
        transaction.setAmount(balance);
        transaction.setTransactionType(type);
        transaction.setCreatedDate(LocalDateTime.now());
        transaction.setPhone(phone);

        if (transactionRepository.createTransaction(transaction) != 0) {
            System.out.println("balance successfully filled ");

        }
    }

    public void getUserTransactionList(ProfileDto profile) {
        transactionRepository.getCardIdByPhone(profile.getPhone());

        List<TransactionDto> transactionDtoList = transactionRepository.userTransaction(profile.getPhone());
        if (transactionDtoList.isEmpty()){
            System.out.println("you don't have transaction ");
            return;
        }
        System.out.println("\t\t\t\t\t\t\t\t**************** Transaction ****************");
        for (TransactionDto transaction : transactionDtoList) {
            System.out.println(transaction);
        }

    }

    public void isExistTransaction() {
        TransactionDto exist = transactionRepository.isExistTransaction();
        if (exist == null){
            terminalRepository.createDefaultTerminal(); //create terminal code=123 address = toshkent
            System.out.println("terminal code 123");
        }
    }

    public void makePayment(String amount, String cardNumber, String terminalCode) {
        double myBalance = cardRepository.getMyBalanceByCardNumber(cardNumber);
        if (myBalance == 0d){
            System.out.println("card not fount");
            return;
        }
        double balance = Double.parseDouble(amount);
        if (myBalance < balance){
            System.out.println("you balance is not enough to payment");
            return;
        }
        CardDto card = cardRepository.getCardByNumber(cardNumber);

        TransactionDto transaction = new TransactionDto();
        transaction.setCardId(card.getId());
        transaction.setCardNumber(card.getCardNumber());
        transaction.setTerminalCode(terminalCode);
        transaction.setAmount(Double.valueOf(amount));
        transaction.setTransactionType(TransactionType.PAYMENT);
        transaction.setCreatedDate(LocalDateTime.now());
        transaction.setPhone(card.getPhone());

        terminalRepository.makePayment( balance,cardNumber);

        terminalRepository.addBalanceToCompanyCard(amount);

        transactionRepository.createTransaction(transaction);

    }

}
