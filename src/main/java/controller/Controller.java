package controller;

import RandomUtils.RandomUtil;
import db.DatabaseUtil;
import dto.CardDto;
import dto.ProfileDto;
import enums.ProfileRole;
import enums.Status;
import service.CardService;
import service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Scanner;

public class Controller {
    String phone, password, name, surname, status, profileDtoRole, createdDate;
    Scanner scannerInt = new Scanner(System.in);
    Scanner scannerStr = new Scanner(System.in);
    UserService userService = new UserService();
    CardService cardService = new CardService();
    ProfileDto profileDto = new ProfileDto();
    CardDto cardDto = new CardDto();

    String cardNumber;
    String numberOfCard;

    Long year = 5L;
    boolean result;
    int option;
    long smallest = 1000_0000_0000_0000L;
    long biggest = 9999_9999_9999_9999L;


    public void start() {
        DatabaseUtil databaseUtil = new DatabaseUtil();
        databaseUtil.createProfileTable();
        databaseUtil.createCardTable();
        databaseUtil.createTerminalTable();
        databaseUtil.createTransactionTable();


        while (true) {
            showMenu();
            option = getAction();

            if (option == 0) {
                return;
            }
            switch (option) {
                case 1 -> login();

                case 2 -> registration();

                default -> System.out.println("Enter another number! ");
            }
        }
    }


    private void login() {
        do {
            System.out.println("Enter phone");
            phone = scannerStr.nextLine();
            System.out.println("Enter password");
            password = scannerStr.nextLine();
        } while (phone == null || password == null);

        profileDto.setPhone(phone);
        profileDto.setPassword(password);

        ProfileDto profile = userService.login(profileDto);
        if (profile == null || profile.getStatus().equals(Status.NO_ACTIVE)) {
            System.out.println("Not found");
        } else {
            if (profile.getProfileRole().equals(ProfileRole.USER)) {
                while (true) {
                    userMenu();

                    option = getAction();

                    if (option == 0) {
                        return;
                    }
                    switch (option) {
                        case 1 -> addCard();
                        case 2 -> cardService.getCardList();
                        case 3 -> changeCardStatus();
                        case 4 -> {}
//                                deleteCard();
                        case 5 -> refill();
                    }
                }

            } else if (profile.getProfileRole().equals(ProfileRole.ADMIN)) {
                while (true) {
                    adminMenu();
                    option = getAction();
                    if (option == 0) {
                        return;
                    }
                    switch (option) {
                        case 1 -> card();
                        case 2 -> terminal();
                        case 3 -> profile();
                        case 4 -> transaction();
                        case 5 -> statistics();
                    }
                }
            }
        }
    }


    private void registration() {

        do {
            System.out.println("Enter name");
            name = scannerStr.nextLine();
            System.out.println("Enter surname");
            surname = scannerStr.nextLine();
            System.out.println("Enter phone");
            phone = scannerStr.nextLine();
            System.out.println("Enter password");
            password = scannerStr.nextLine();

        } while (name == null || surname == null || phone == null || password == null);

        profileDto.setName(name);
        profileDto.setSurname(surname);
        profileDto.setPhone(phone);
        profileDto.setPassword(password);
        profileDto.setProfileRole(ProfileRole.USER);
        profileDto.setCreatedDate(LocalDateTime.now());
        profileDto.setStatus(Status.ACTIVE);


        boolean result = userService.registration(profileDto);
        if (result) {
            System.out.println("successfully registered");
            userMenu();
        } else {
            System.out.println("registration error");
        }
    }

    private void adminMenu() {
        System.out.println("""
                                
                1.Card
                2.Terminal
                3.Profile
                4.Transaction
                5.Statistic
                0.Exit
                """);
    }

    private void userMenu() {
        System.out.println(""" 
                                
                1.Add card
                2.Card list
                3.Card change status
                4.delete card
                5.Refill
                0.Exit
                """);
    }

    private void showMenu() {
        System.out.println("""
                                
                1.Login
                2.Registration
                0.Exit
                """);
    }

    public int getAction() {
        System.out.print("Choose menu ");
        return scannerInt.nextInt();
    }

    private void card() {

        System.out.println("""
                                
                1. Create Card
                2. Card List
                3. Profile List
                4. Update Card
                5. Change Card status
                6. Delete Card
                0. Exit
                """);

        option = getAction();
        if (option == 0) {
            return;
        }
        switch (option) {
            case 1 -> createCard();
            case 2 -> cardService.getCardList();
            case 3 -> userService.getAllProfileList();
            case 4 -> updateCard();
            case 5 -> changeCardStatus();
            case 6 -> deleteCard();
            default -> System.out.println("wrong input");

        }
    }

    private void statistics() {
    }

    private void transaction() {
    }

    private void profile() {
    }

    private void terminal() {

    }

    private boolean updateCard() {

        do {
            System.out.println("Enter card number");
            numberOfCard = scannerStr.nextLine();
        } while (numberOfCard.trim().isEmpty());

        cardDto.setCardNumber(numberOfCard);
        cardDto.setExpireDate(LocalDate.now().plusYears(year));
        cardService.updateCard(cardDto);

        return false;
    }

    private boolean createCard() {

        cardNumber = String.valueOf(RandomUtil.getRandomNumber(smallest, biggest));
        cardDto.setCardNumber(cardNumber);
        cardDto.setBalance(0.0);
        cardService.createCard(cardDto);
        return true;
    }

    private void refill() {

    }

    private void deleteCard() {
        System.out.println("Enter card number");
        String cardNumber = scannerStr.nextLine();

        CardDto cardDto1 = new CardDto();
        cardDto1.setCardNumber(cardNumber);

        boolean bool = cardService.deleteCard(cardDto1);
        if (bool){
            System.out.println(" successfully deleted");

        }else {
            System.out.println("deleted error");

        }
    }

    private void changeCardStatus() {

    }

    private void addCard() {
        ProfileDto profileDto = new ProfileDto();
        do {
            System.out.println("Enter name");
            name = scannerStr.nextLine();
            System.out.println("Enter surname");
            surname = scannerStr.nextLine();
            System.out.println("Enter phone");
            phone = scannerStr.nextLine();

        } while (name.trim().isEmpty() || surname.trim().isEmpty() || phone.trim().isEmpty());

        profileDto.setName(name);
        profileDto.setSurname(surname);
        profileDto.setPhone(phone);


        boolean result = cardService.addCard(profileDto);
        if (result) {
            System.out.println("successfully add");
        } else {
            System.out.println("Not found this user ");
        }
    }

}

