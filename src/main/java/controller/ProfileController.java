package controller;

import dto.ProfileDto;
import dto.TransactionDto;
import service.CardService;
import service.ProfileService;
import service.TransactionService;
import utils.ScannerUtil;

import java.util.List;
import java.util.Scanner;

public class ProfileController {

    Scanner scannerStr = new Scanner(System.in);
    String phone, cardNumber, terminalCode;
    CardService cardService = new CardService();
    TransactionService transactionService = new TransactionService();

    int option;
    boolean bool;

    public void start(ProfileDto profile) {
        bool = true;
        while (bool) {
            menu();
            option = ScannerUtil.getOption();
            switch (option) {
                case 1 -> addCard(profile);
                case 2 -> profileCardList(profile);
                case 3 -> changeCardStatus();
                case 4 -> deleteCard();
                case 5 -> reFill();
                case 6 -> transactionList(profile);
                case 7 -> makePayment();
                case 0 -> bool = false;
            }
        }
    }


    private void menu() {
        System.out.println();
        System.out.println("1. Add Card");
        System.out.println("2. Card List ");
        System.out.println("3. Card Change Status");
        System.out.println("4. Delete Card");
        System.out.println("5. ReFill ");
        System.out.println("6. Transaction List");
        System.out.println("7. Make Payment");
        System.out.println("0. Log out");
        System.out.println();

    }

    /**
     * Card
     */

    private void addCard(ProfileDto profile) {

        if (cardService.addCard(profile)) {
            System.out.println("successfully add");
            return;
        }
        System.out.println("Not found this user ");

    }

    private void profileCardList(ProfileDto profile) {
        cardService.getMyCardList(profile);
    }

    private void changeCardStatus() {
        System.out.println("Enter card number");
        String cardNumber = scannerStr.nextLine();
        cardService.changeCardStatus(cardNumber);
    }

    private void deleteCard() {
        System.out.println("Enter card number");
        String cardNumber = scannerStr.nextLine();
        cardService.profileDeleteCard(cardNumber);
    }

    private void reFill() {
        transactionService.isExistTransaction();  // create default terminal code = 123

        String balances;
        do {
            System.out.println("Enter card number ");
            cardNumber = scannerStr.nextLine();

            System.out.println("Enter balance");
            balances = scannerStr.nextLine();

            System.out.println("Enter  phone");
            phone = scannerStr.nextLine();

            System.out.println("Enter  terminal code");
            terminalCode = scannerStr.nextLine();


        } while (cardNumber.trim().isEmpty() || balances.trim().isEmpty() || phone.trim().isEmpty());


        cardService.refill(phone, cardNumber, Double.parseDouble(balances), terminalCode);

    }

    private void transactionList(ProfileDto profile) {
        transactionService.getUserTransactionList(profile);

    }

    private void makePayment() {
        System.out.println("Enter amount");
        String amount = scannerStr.nextLine();

        System.out.println("Enter card number");
        String cardNumber = scannerStr.nextLine();

        System.out.println("Enter terminal code");
        String terminalCode = scannerStr.nextLine();

        transactionService.makePayment(amount,cardNumber,terminalCode);

    }
}


