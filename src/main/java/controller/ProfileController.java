package controller;

import db.DatabaseUtil;
import dto.ProfileDto;
import enums.ProfileRole;
import enums.Status;
import service.ProfileService;

import java.time.LocalDateTime;
import java.util.Scanner;

public class ProfileController {
    CardController cardController = new CardController();

    Scanner scannerStr = new Scanner(System.in);
    Scanner scannerInt = new Scanner(System.in);
    String phone, password, name, surname, status, profileDtoRole, createdDate;
    ProfileService userService = new ProfileService();
    ProfileDto profileDto = new ProfileDto();
    int option;



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
//            if (option == 0) {return;}
//            switch (option) {
//                case 1 -> login();
//                case 2 -> registration();
//                default -> System.out.println("Enter another number! ");
//            }
//        }
//    }

//    private void login() {
//        do {
//            System.out.println("Enter phone");
//            phone = scannerStr.nextLine();
//            System.out.println("Enter password");
//            password = scannerStr.nextLine();
//        } while (phone.trim().isEmpty() || password.trim().isEmpty());
//
//        profileDto.setPhone(phone);
//        profileDto.setPassword(password);
//
//        ProfileDto profile = userService.login(profileDto);
//        if (profile == null || profile.getStatus().equals(Status.NO_ACTIVE)) {
//            System.out.println("Not found");
//        } else {
//            if (profile.getProfileRole().equals(ProfileRole.USER)) {
//                while (true) {
//                    userMenu();
//
//                    option = getAction();
//
//                    if (option == 0){ return;}
//
//                    switch (option) {
//                        case 1 -> cardController.addCard();
//                        case 2 -> cardService.getCardList();
//                        case 3 -> changeCardStatus();
//                        case 4 -> {}
////                                deleteCard();
//                        case 5 -> refill();
//                    }
//                }
//
//            } else if (profile.getProfileRole().equals(ProfileRole.ADMIN)) {
//                while (true) {
//                    adminMenu();
//                    option = getAction();
//                    if (option == 0) {
//                        return;
//                    }
//                    switch (option) {
//                        case 1 -> card();
//                        case 2 -> terminal();
//                        case 3 -> profile();
//                        case 4 -> transaction();
//                        case 5 -> statistics();
//                    }
//                }
//            }
//        }
//    }

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
                2.Registration
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


    public int getAction() {
        System.out.print("Choose menu ");
        return scannerInt.nextInt();
    }



}
