package controller;

import db.DatabaseUtil;
import dto.CardDto;
import dto.ProfileDto;
import dto.TerminalDto;
import enums.ProfileRole;
import enums.Status;
import service.CardService;
import service.ProfileService;
import service.TerminalService;
import service.TransactionService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Controller {
    String phone, password, name, surname;
    Scanner scannerInt = new Scanner(System.in);
    Scanner scannerStr = new Scanner(System.in);
    ProfileService userService = new ProfileService();
    CardService cardService = new CardService();
    TransactionService transactionService = new TransactionService();
    TerminalService terminalService = new TerminalService();
    ProfileDto profileDto = new ProfileDto();
    CardDto cardDto = new CardDto();


    String numberOfCard;

    Long year = 5L;
    boolean result;
    int option;


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

                userRoleMenu(profile);

            } else if (profile.getProfileRole().equals(ProfileRole.ADMIN)) {

                adminRoleMenu();

            }
        }
    }

    private void adminRoleMenu() {
        while (true) {
            adminMenu();

            option = getAction();
            if (option == 0) {
                return;
            }
            switch (option) {
                case 1 -> showCardMenuForAdmin();
                case 2 -> terminal();
                case 3 -> profile();
                case 4 -> transaction();
                case 5 -> statistics();
            }

        }
    }

    private void userRoleMenu(ProfileDto profileDto) {
        while (true) {
            userMenu();
            option = getAction();
            if (option == 0) {
                return;
            }

            switch (option) {
                case 1 -> addCard(profileDto);
                case 2 -> cardService.getMyCardList(profileDto);
                case 3 -> {
                }
                case 4 -> {
                }
                case 5 -> refill();
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
                                
                1.Registration card
                2.My card list
                3.Change card status
                4.Delete card
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

    private void showCardMenuForAdmin() {

        while (true) {

            System.out.println("""
                                    
                    1. Create Card
                    2. Card List
                    3. Update Card
                    4. Change Card status
                    5. Delete Card
                    0. Exit
                    """);
            option = getAction();
            if (option == 0) {
                return;
            }

            switch (option) {
                case 1 -> cardService.createCard();
                case 2 -> cardService.getCardList();
                case 3 -> updateCard();
                case 4 -> changeCardStatus();
                case 5 -> deleteCard();
                default -> System.out.println("wrong input");
            }
        }
    }


    private void statistics() {
    }

    private void transaction() {
    }

    private void profile() {
    }

    private void terminal() {
        while (true) {
            System.out.println("""
                    1. Create Terminal (code unique,address)
                    2. Terminal List
                    3. Update Terminal (code,address)
                    4. Change Terminal Status
                    5. Delete
                     """);

            option = getAction();
            if (option == 0) {
                return;
            }
            switch (option) {
                case 1 -> createTerminal();
                case 2 -> {
                }
                case 3 -> {
                }
                case 4 -> {
                }
                case 5 -> {
                }
            }

        }


    }

    public void createTerminal() {
        TerminalDto terminalDto = new TerminalDto();

        System.out.println("enter country");
        String country = scannerStr.nextLine();
        System.out.println("enter code");
        String code = scannerStr.nextLine();

        terminalDto.setAddress(country);
        terminalDto.setCode(code);

        boolean terminal = terminalService.createTerminal(terminalDto);
        if (terminal) {
            System.out.println("successfully created terminal");
        }
        System.out.println("created terminal failed");
    }

    private void updateCard() {

        do {
            System.out.println("Enter card number");
            numberOfCard = scannerStr.nextLine();
            System.out.println("Enter expire date");
        } while (numberOfCard.trim().isEmpty());

        cardDto.setCardNumber(numberOfCard);
        cardDto.setExpireDate(LocalDate.now().plusYears(year));
        cardService.updateCard(cardDto);

    }

    private void refill() {

    }

    private void deleteCard() {
        System.out.println("Enter card number");
        String cardNumber = scannerStr.nextLine();

        CardDto cardDto = new CardDto();
        cardDto.setCardNumber(cardNumber);

        boolean bool = cardService.deleteCard(cardDto);
        if (bool) {
            System.out.println(" successfully deleted");

        } else {
            System.out.println("deleted failed");
        }
    }

    private void changeCardStatus() {
        System.out.println("Enter card number");
        String cardNumber = scannerStr.nextLine();
        System.out.println("Enter phone number");
        String phone = scannerStr.nextLine();

        boolean bool = cardService.changeCardStatus(cardNumber, phone);
        if (bool) {
            System.out.println("status changed");
        }
        System.out.println("error  to changed status");
    }

    private void addCard(ProfileDto profileInfo) {
        ProfileDto profileDto = new ProfileDto();

        profileDto.setName(profileInfo.getName());
        profileDto.setSurname(profileInfo.getSurname());
        profileDto.setPhone(profileInfo.getPhone());

        boolean result = cardService.addCard(profileDto);
        if (result) {
            System.out.println("successfully add");
        } else {
            System.out.println("Not found this user ");
        }
    }
}




