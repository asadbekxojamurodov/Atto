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
    RandomUtil randomUtil = new RandomUtil();
    String cardNumber = "1234567";
    String exp_date;
    Long year = 5L;



    public void start() {
        DatabaseUtil databaseUtil = new DatabaseUtil();
        databaseUtil.createProfileTable();
        databaseUtil.createCardTable();
        databaseUtil.createTerminalTable();
        databaseUtil.createTransactionTable();


        while (true) {
            showMenu();
            switch (getAction()) {
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
                userMenu();
                switch (getAction()) {
                    case 1 -> {

                    }
                    case 2 -> {
                    }
                    case 3 -> {
                    }
                    case 4 -> {
                    }
                    case 5 -> {
                    }
                }
            } else if (profile.getProfileRole().equals(ProfileRole.ADMIN)) {
                adminMenu();
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

    private void showMenu() {
        System.out.println("""
                1.Login
                2.Registration""");
    }

    public int getAction() {
        System.out.print("Choose menu ");
        return scannerInt.nextInt();
    }

    //*** Admin Menu ***
//            (Card)
//            1. Create Card(number,exp_date)
//    2. Card List
//    3. Update Card (number,exp_date)
//    4. Change Card status
//    5. Delete Card
//
//            (Terminal)
//    6. Create Terminal (code unique,address)
//    7. Terminal List
//    8. Update Terminal (code,address)
//    9. Change Terminal Status
//    10. Delete
//
//            (Profile)
//    11. Profile List
//    12. Change Profile Status (by phone)
    private void adminMenu() {
        System.out.println("""
                1.Card
                2.Terminal
                3.Profile
                4.Transaction
                5.Statistic """);
        switch (getAction()){
            case 1-> card();
            case 2->{}
            case 3->{}
            case 4->{}
            case 5->{}

        }
    }

    private void card() {
        System.out.println("""
                1. Create Card(number,exp_date)
                2. Card List
                3. Update Card (number,exp_date)
                4. Change Card status
                5. Delete Card """);
        switch (getAction()){
            case 1 -> createCard();
            case 2 ->cardService.getCardList();
            case 3 ->{}
            case 4 ->{}
            case 5 ->{}

        }
    }

    private boolean createCard() {

        cardNumber += String.valueOf(RandomUtil.getRandomNumber(100_000_000,999_999_999));

        cardDto.setCardNumber(cardNumber);
        cardDto.setExpireDate(LocalDate.now().plusYears(year));

        cardDto.setStatus(Status.NO_ACTIVE);
        cardDto.setCreatedDate(LocalDateTime.now());

        cardService.createCard(cardDto);

        return true;
    }


    //*** User  Menu **
//            (Card)
//            1. Add Card (number) - (cartani profile ga ulab qo'yamiz.) (added_date)
//           Enter Card Number:
//            (kiritilgan number database da bo'lishi kerak.)
//            2. Card List (number,exp_date,balance)
//    3. Card Change Status
//    4. Delete Card (visible_user = false,deleted_user)
//
//    4. ReFill (pul tashlash) (carta userga tegishli bo'lishi kerak.)
//    Enter card number:
//    Balance:
//            (Transaction with type 'ReFill')
    private void userMenu() {
        System.out.println(""" 
                1.Add card (number) - (cartani profile ga ulab qo'yamiz.) (added_date) Enter Card Number:
                2.Card list (number,exp_date,balance)
                3.Card change status 
                4.delete card  (visible_user = false,deleted_user)
                5.Refill  (pul tashlash) (carta userga tegishli bo'lishi kerak.)
                             Enter card number:
                             Balance:
                             (Transaction with type 'ReFill')
                """);

        switch (getAction()) {
            case 1 -> addCard();
            case 2 -> cardService.getCardList();
            case 3 -> changeCardStatus();
            case 4 -> deleteCard();
            case 5 -> refill();
        }
    }


    private void refill() {
    }

    private void deleteCard() {
    }

    private void changeCardStatus() {
    }




    private void addCard() {
    }

}

