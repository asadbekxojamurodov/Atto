package controller;

import dto.ProfileDto;
import service.AuthService;
import utils.ScannerUtil;

import java.util.Scanner;

public class AuthController {
    String name, surname, password, phone;
    Scanner scannerStr = new Scanner(System.in);
    ProfileDto profile = new ProfileDto();
    AuthService authService = new AuthService();

    public void start() {
        boolean bool = true;

        while (bool) {
            menu();
            int option = ScannerUtil.getOption();
            switch (option) {
                case 1 -> login();
                case 2 -> registration();
                case 0 -> bool = false;

            }
        }
    }


    private void menu() {
        System.out.println("""
                ********************Menu***********************             
                         1. Login >
                         2. Registration >
                         0. Exit >
                         """);
    }

    private void login() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter phone:");
        String phone = scanner.nextLine();

        System.out.print("Enter password:");
        String password = scanner.next();

        authService.login(phone, password);
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

        } while (name.trim().isEmpty() || surname.trim().isEmpty() || phone.trim().isEmpty() || password.trim().isEmpty());

        profile.setName(name);
        profile.setSurname(surname);
        profile.setPhone(phone);
        profile.setPassword(password);

        authService.registration(profile);

    }


}
