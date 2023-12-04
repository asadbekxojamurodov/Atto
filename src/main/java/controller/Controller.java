//package controller;
//
//import db.DatabaseUtil;
//import dto.CardDto;
//import dto.ProfileDto;
//import dto.TerminalDto;
//import enums.ProfileRole;
//import enums.Status;
//import repository.CardRepository;
//import service.CardService;
//import service.ProfileService;
//import service.TerminalService;
//import service.TransactionService;
//
//import java.time.LocalDateTime;
//import java.util.Scanner;
//
//public class Controller {
//    String phone, password, name, surname;
//    Scanner scannerInt = new Scanner(System.in);
//    Scanner scannerStr = new Scanner(System.in);
//    Scanner scannerDbl = new Scanner(System.in);
//    CardRepository cardRepository = new CardRepository();
//    ProfileService userService = new ProfileService();
//    CardService cardService = new CardService();
//    TransactionService transactionService = new TransactionService();
//    TerminalService terminalService = new TerminalService();
//    ProfileDto profileDto = new ProfileDto();
//    TerminalDto terminalDto = new TerminalDto();
//    CardDto cardDto = new CardDto();
//    String numberOfCard;
//    Long year = 5L;
//    int option;
//    boolean bool;
//   static String cardNumber;
//   static String balance;
//
//
//    public void start() {
//        DatabaseUtil databaseUtil = new DatabaseUtil();
//        databaseUtil.createProfileTable();
//        databaseUtil.createCardTable();
//        databaseUtil.createTerminalTable();
//        databaseUtil.createTransactionTable();
//
//        while (true) {
//            showMenu();
//            option = getAction();
//            if (option == 0) {
//                return;
//            }
//            switch (option) {
//                case 1 -> login();
//                case 2 -> registration();
//                default -> System.out.println("Enter another number! ");
//            }
//        }
//    }
///////////////////////////////////////////////////
//
//    //   sign in
//    private void login() {
//        do {
//            System.out.println("Enter phone");
//            phone = scannerStr.nextLine();
//            System.out.println("Enter password");
//            password = scannerStr.nextLine();
//        } while (phone == null || password == null);
//
//        profileDto.setPhone(phone);
//        profileDto.setPassword(password);
//
//        ProfileDto profile = userService.login(profileDto);
//
//        if (profile == null || profile.getStatus().equals(Status.NO_ACTIVE)) {
//            System.out.println("Not found");
//        } else {
//            if (profile.getProfileRole().equals(ProfileRole.USER)) {
//
//                userRoleMenu(profile);
//
//            } else if (profile.getProfileRole().equals(ProfileRole.ADMIN)) {
//
//                adminRoleMenu();
//
//            }
//        }
//    }
//
////    sign up
//
//    private void registration() {
//
////        do {
////            System.out.println("Enter name");
////            name = scannerStr.nextLine();
////            System.out.println("Enter surname");
////            surname = scannerStr.nextLine();
////            System.out.println("Enter phone");
////            phone = scannerStr.nextLine();
////            System.out.println("Enter password");
////            password = scannerStr.nextLine();
////
////        } while (name == null || surname == null || phone == null || password == null);
//
//        profileDto.setName(name);
//        profileDto.setSurname(surname);
//        profileDto.setPhone(phone);
//        profileDto.setPassword(password);
//        profileDto.setProfileRole(ProfileRole.USER);
//        profileDto.setCreatedDate(LocalDateTime.now());
//        profileDto.setStatus(Status.ACTIVE);
//
//
//        boolean result = userService.registration(profileDto);
//        if (result) {
//            System.out.println("successfully registered");
//            userMenu();
//        } else {
//            System.out.println("registration error");
//        }
//    }
//
//////////////////////////////////////////////////////
//
//    //    Role
//    private void userRoleMenu(ProfileDto profileDto) {
//        while (true) {
//            userMenu();
//            option = getAction();
//            if (option == 0) {
//                return;
//            }
//
//            switch (option) {
//                case 1 -> addCard(profileDto);
//                case 2 -> cardService.getMyCardList(profileDto);
//                case 3 -> changeCardStatus();
//                case 4 -> {}
//                case 5 -> refill();
//            }
//        }
//    }
//
//    private void adminRoleMenu() {
//        while (true) {
//            adminMenu();
//
//            option = getAction();
//            if (option == 0) {
//                return;
//            }
//            switch (option) {
//                case 1 -> showCardMenuForAdmin();
//                case 2 -> showTerminalMenu();
//                case 3 -> showProfileMenu();
//                case 4 -> showTransactionMenu();
//                case 5 -> showStatisticsMenu();
//            }
//
//        }
//    }
///////////////////////////////////////////////////////
//
//    //    ADMIN MENU  METHODS
//    private void adminMenu() {
//        System.out.println("""
//
//                1.Card
//                2.Terminal
//                3.Profile
//                4.Transaction
//                5.Statistic
//                0.Exit
//                """);
//    }
//    private void showCardMenuForAdmin() {
//
//        while (true) {
//
//            System.out.println("""
//
//                    1. Create Card
//                    2. Card List
//                    3. Update Card
//                    4. Change Card status
//                    5. Delete Card
//                    0. Exit
//                    """);
//            option = getAction();
//            if (option == 0) {
//                return;
//            }
//
//            switch (option) {
//                case 1 -> cardService.createCard(year);
//                case 2 -> cardService.getCardList();
//                case 3 -> updateCard();
//                case 4 -> changeCardStatusByAdmin();
//                case 5 -> deleteCard();
//                default -> System.out.println("wrong input");
//            }
//        }
//    }
//
//    private void showTerminalMenu() {
//        while (true) {
//            System.out.println("""
//
//                    1. Create Terminal (code unique,address)
//                    2. Terminal List
//                    3. Update Terminal (code,address)
//                    4. Change Terminal Status
//                    5. Delete
//                     """);
//
//            option = getAction();
//            if (option == 0) {
//                return;
//            }
//            switch (option) {
//                case 1 -> createTerminal();
//                case 2 -> getTerminalList();
//                case 3 -> updateTerminal();
//                case 4 -> changeTerminalStatus();
//                case 5 -> delete();
//            }
//        }
//    }
//
//    private void showProfileMenu() {
//        while (true) {
//            System.out.println("""
//
//                    1. Profile List
//                    2. Change Profile Status (by phone)
//                    """);
//
//            option = getAction();
//            if (option == 0) {
//                return;
//            }
//            switch (option) {
//                case 1 -> userService.getAllProfileList();
//                case 2 -> changeProfileStatus();
//
//            }
//        }
//    }
//
//    private void showTransactionMenu() {
//
//        while (true) {
//            System.out.println("""
//
//                    1. Transaction List
//                    CardNumber, TerminalNumber, Amount,TransactionDate,Type (oxirgi birinchi ko'rinadi)
//
//                    2. Company Card Balance
//                    (card bo'ladi shu cardga to'lovlar tushadi. bu sql da insert qilinga bo'ladi)
//                    """);
//
//            option = getAction();
//            if (option == 0) {
//                return;
//            }
//
//            switch (option) {
//                case 1 -> transactionList();
//                case 2 -> companyCardBalance();
//            }
//
//        }
//    }
//
//
//    private void companyCardBalance() {
//        //
//    }
//
//    private void transactionList() {
//
//    }
//
//    private void showStatisticsMenu() {
////        15. Bugungi to'lovlar
////        CardNumber, TerminalNumber, Amount,TransactionDate,Type (oxirgi birinchi ko'rinadi)
////        16. Kunlik to'lovlar (bir kunlik to'lovlar):
////        Enter Date: yyyy-MM-dd
////        CardNumber, TerminalNumber, Amount,TransactionDate,Type (oxirgi birinchi ko'rinadi)
////        17. Oraliq to'lovlar:
////        Enter FromDate: yyyy-MM-dd
////        Enter ToDate:   yyyy-MM-dd
////        18. Umumiy balance (company card dagi pulchalar)
////        19. Transaction by Terminal:
////        Enter terminal number:
////        20. Transaction By Card:
////        Enter Card number:
//
//        while (true) {
//            System.out.println("""
//
//                    1. Today's payments
//                    2. Daily payments
//                    3. Interim payments
//                    4. Total balance
//                    5. Transaction by Terminal
//                    6. Transaction By Card
//                    0. Exit
//                    """);
//
//            option = getAction();
//            if (option == 0) {
//                return;
//            }
//            switch (option) {
//                case 1 -> {}
//                case 2 -> {}
//                case 3 -> {}
//                case 4 -> {}
//                case 5 -> {}
//                case 6 -> {}
//
//            }
//        }
//    }
//
//
//
//
//    private void changeCardStatusByAdmin() {
//        System.out.println("Enter card number");
//        String cardNumber = scannerStr.nextLine();
//        System.out.println("Enter phone number");
//        String phone = scannerStr.nextLine();
//
//        boolean bool = cardService.changeCardStatusByAdmin(cardNumber, phone);
//        if (bool) {
//            System.out.println("status changed");
//        }
//        System.out.println("error  to changed status");
//    }
//
//    private void changeProfileStatus() {
////        System.out.println("Enter phone");
////        String phone = scannerStr.nextLine();
////        profileDto.setPhone(phone);
////        bool = userService.changeProfileStatus(profileDto);
////        if (bool) {
////            System.out.println("successfully changed status");
////        }
////        System.out.println("not found profile");
//    }
//
//    public void delete() {
////        TerminalDto terminal = new TerminalDto();
////        System.out.println("enter terminal code");
////        String code = scannerStr.nextLine();
////        terminal.setCode(code);
////        bool = terminalService.delete(terminal);
////        if (bool) {
////            System.out.println("successfully deleted");
////        }
////        System.out.println("not found this terminal");
//
//    }
//
//    private void changeTerminalStatus() {
//
////        System.out.println("enter terminal code");
////        String code = scannerStr.nextLine();
////        terminalDto.setCode(code);
////        boolean bool = terminalService.changeTerminalStatus(terminalDto);
////        if (bool) {
////            System.out.println("successfully changed status");
////        } else {
////            System.out.println("not found this terminal");
////        }
//    }
//
//    public void getTerminalList() {
////        boolean terminalList = terminalService.getTerminalList();
////        if (terminalList) {
////            System.out.println("all terminal");
////        } else {
////            System.out.println("not found terminal");
////        }
//    }
//
//    private void updateTerminal() {
////        TerminalDto terminal = new TerminalDto();
////
////        System.out.println("enter terminal code");
////        String code = scannerStr.nextLine();
////        System.out.println("Enter new address for terminal");
////        String address = scannerStr.nextLine();
////
////        terminal.setCode(code);
////        terminal.setAddress(address);
////
////        boolean bool = terminalService.updateTerminalByCode(terminal);
////        if (bool) {
////            System.out.println("successfully updated address to " + address);
////        } else {
////            System.out.println("not found this terminal");
////        }
//    }
//
//    public void createTerminal() {
//        TerminalDto terminalDto = new TerminalDto();
//
//        String country, code;
//
//        do {
//            System.out.println("enter country");
//            country = scannerStr.nextLine();
//            System.out.println("enter code");
//            code = scannerStr.nextLine();
//
//        } while (country.trim().isEmpty() || code.trim().isEmpty());
//
//
//        terminalDto.setAddress(country);
//        terminalDto.setCode(code);
//
//        boolean terminal = terminalService.createTerminal(terminalDto);
//        if (terminal) {
//            System.out.println("successfully created terminal");
//        }
//        System.out.println("created terminal failed");
//    }
//
//    private void updateCard() {
//        System.out.print("Enter card number: ");
//        Scanner scanner = new Scanner(System.in);
//        String cardNumber = scanner.nextLine();
//
//        System.out.print("Enter card expired date (yyyy.MM.dd): ");
//        String expiredDate = scanner.nextLine();
//
//        cardService.updateCard(cardNumber,expiredDate);
//
//    }
//
//    private void deleteCard() {
//        System.out.println("Enter card number");
//        String cardNumber = scannerStr.nextLine();
//
//        CardDto cardDto = new CardDto();
//        cardDto.setCardNumber(cardNumber);
//
//        boolean bool = cardService.deleteCard(cardDto);
//        if (bool) {
//            System.out.println(" successfully deleted");
//
//        } else {
//            System.out.println("deleted failed");
//        }
//    }
//
//
//////////////////////////////////////////////////
//
////    PROFILE MENU
//
//    private void userMenu() {
//        System.out.println("""
//
//                1.Registration card
//                2.My card list
//                3.Change card status
//                4.Delete card
//                5.Refill
//                0.Exit
//                """);
//    }
//
//    private void addCard(ProfileDto profileInfo) {
//        ProfileDto profileDto = new ProfileDto();
//
//        profileDto.setName(profileInfo.getName());
//        profileDto.setSurname(profileInfo.getSurname());
//        profileDto.setPhone(profileInfo.getPhone());
//
//        boolean result = cardService.addCard(profileDto);
//        if (result) {
//            System.out.println("successfully add");
//        } else {
//            System.out.println("Not found this user ");
//        }
//    }
//
//    private void changeCardStatus() {
////        System.out.println("Enter card number");
////        String cardNumber = scannerStr.nextLine();
////        System.out.println("Enter phone number");
////        String phone = scannerStr.nextLine();
////
////        boolean bool = cardService.changeCardStatus(cardNumber);
////        if (bool) {
////            System.out.println("status changed");
////        }
////        System.out.println("error  to changed status");
//    }
//
//    private void refill() {
//        CardDto cardDto = new CardDto();
//        String balances=null;
//        do {
//            System.out.println("Enter card number ");
//            cardNumber = scannerStr.nextLine();
//
//            System.out.println("Enter balance");
//            balances = scannerStr.nextLine();
//
//            System.out.println("Enter  phone");
//            phone = scannerStr.nextLine();
//
//        }while (cardNumber.trim().isEmpty() || balances.trim().isEmpty() || phone.trim().isEmpty() );
//
//
//        cardDto.setCardNumber(cardNumber);
//        cardDto.setPhone(phone);
//
////         cardService.refill(cardDto, Double.parseDouble(balances));
////        if (refills) {
////             bool = cardRepository.setBalanceCompanyCard(cardDto);
////             if (bool){
////                 System.out.println("successfully fill balance");
////             }
////             else {
////                 System.out.println(" failed");
////             }
////            System.out.println("successfully fill balance");
////        } else {
////            System.out.println(" failed");
////        }
//    }
//
//
//////////////////////////////////////////////////
//
//    private void showMenu() {
//        System.out.println("""
//
//                1.Login
//                2.Registration
//                0.Exit
//                """);
//    }
//
//    public int getAction() {
//        System.out.print("Choose menu ");
//        return scannerInt.nextInt();
//    }
//
//
//}
//
//
//
//
