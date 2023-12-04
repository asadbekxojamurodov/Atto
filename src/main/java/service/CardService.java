package service;

import RandomUtils.RandomUtil;
import dto.CardDto;
import dto.ProfileDto;
import enums.Status;
import enums.TransactionType;
import repository.CardRepository;
import repository.ProfileRepository;
import repository.TransactionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CardService {
    CardRepository cardRepository = new CardRepository();
    ProfileRepository profileRepository = new ProfileRepository();
    TransactionService transactionService = new TransactionService();

    long smallest = 1000_0000_0000L;
    long biggest = 9999_9999_9999L;


    public void createCard(long year) {
        String cardNumber = "1234";
        CardDto card = new CardDto();
        card.setCardNumber(cardNumber + RandomCardNumber());

        card.setBalance(0.0);
        card.setStatus(Status.NO_ACTIVE);
        card.setCreatedDate(LocalDateTime.now());
        card.setExpireDate(LocalDate.now().plusYears(year));
        cardRepository.createCard(card);
    }


    public String RandomCardNumber() {
        return String.valueOf(RandomUtil.getRandomNumber(smallest, biggest));
    }



    public void getCardList() {
        List<CardDto> allCard = cardRepository.getAllCard();
        System.out.println("\t\t\t\t\t\t\t\t\t\t**************** Card list ****************\n");
        for (CardDto cardDto : allCard) {
            System.out.println(cardDto);
        }
    }

    public boolean addCard(ProfileDto profileDto) {

        List<ProfileDto> allProfile = profileRepository.getAllProfile();

        for (ProfileDto dto : allProfile) {
            if (profileDto.getName().equals(dto.getName()) && profileDto.getSurname().equals(dto.getSurname()) && profileDto.getPhone().equals(dto.getPhone())) {
                return setCardForUser(profileDto);
            }
        }
        return false;
    }

    public boolean setCardForUser(ProfileDto profileDto) {

        List<CardDto> allNoActiveCardList = cardRepository.getAllNoActiveCardList();

        for (CardDto cardDto : allNoActiveCardList) {

            cardDto.setPhone(profileDto.getPhone());
            cardDto.setStatus(Status.ACTIVE);

            return cardRepository.addCard(cardDto);
        }
        return false;
    }


    public void getMyCardList(ProfileDto profileDto) {

        List<CardDto> allCard = cardRepository.getAllMyCard(profileDto.getPhone());
        System.out.println("\t\t\t\t\t\t\t\t\t\t**************** Card list ****************\n");
        for (CardDto cardDto : allCard) {
            System.out.println(cardDto);
//            System.out.println("CARD NUMBER < " + cardDto.getCardNumber()
//                    + " > EXPIRE DATE < " + cardDto.getExpireDate()
//                    + " > BALANCE < " + cardDto.getBalance() + " >");
        }
    }


    public void changeCardStatus(String cardNumber) {

        CardDto existCard = cardRepository.getCardByNumber(cardNumber);
        if (existCard == null) {
            System.out.println("card not found");
            return;
        }

        if (existCard.getStatus().equals(Status.ACTIVE)) {
            existCard.setStatus(Status.BLOCKED);
            cardRepository.changeCardStatus(existCard);

        } else if (existCard.getStatus().equals(Status.BLOCKED)) {
            existCard.setStatus(Status.ACTIVE);
            cardRepository.changeCardStatus(existCard);

        }
    }



    public void refill(String phone, String cardNumber, double balance, String terminalCode) {

        CardDto card = cardRepository.getCardByNumber(cardNumber);

        if (card == null) {
            System.out.println("this card not belong to you");
            return;
        }

        if (card.getPhone() == null || !card.getPhone().equals(phone)) {
            System.out.println(" card not belongs to you.");
            return;
        }

        cardRepository.refill(cardNumber, balance);

        transactionService.createTransaction(card.getId(), cardNumber, terminalCode, balance, TransactionType.REFILL,phone);

    }

    public void adminUpdateCard(String cardNumber, String expireDate) {
        CardDto exist = cardRepository.getCardByNumber(cardNumber);
        if (exist == null) {
            System.out.println("Card not found");
            return;
        }

        CardDto card = new CardDto();
        card.setCardNumber(cardNumber);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDate localDate = LocalDate.parse(expireDate, timeFormatter);

        if (localDate.isBefore(LocalDate.now())) {
            System.out.println("locale Date must be bigger than now");
            return;
        }

        card.setExpireDate(localDate);

        int n = cardRepository.AdminUpdateCard(card);
        if (n != 0) {
            System.out.println("Card Updated");
        }
    }

    public void adminChangeCardStatus(String cardNumber) {
        CardDto exist = cardRepository.getCardByNumber(cardNumber);
        if (exist == null) {
            System.out.println("Card not found");
            return;
        }

        if (exist.getStatus().equals(Status.ACTIVE)) {
            cardRepository.updateCardStatus(cardNumber, Status.BLOCKED);

        } else if (exist.getStatus().equals(Status.BLOCKED)) {
            cardRepository.updateCardStatus(cardNumber, Status.ACTIVE);

        }
    }

    public int adminDeleteCard(String cardNumber) {
        CardDto exist = cardRepository.getCardByNumber(cardNumber);
        if (exist == null) {
            System.out.println("Card not found");
            return 0;
        }

        return cardRepository.adminDeleteCard(cardNumber);
    }

    public void profileDeleteCard(String cardNumber) {
        CardDto exist = cardRepository.getCardByNumber(cardNumber);
        if (exist == null) {
            System.out.println("Card not found");
            return;
        }
        cardRepository.profileDeleteCard(exist);

        System.out.println("Card deleted ");

    }
}





