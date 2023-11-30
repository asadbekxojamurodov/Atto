package utils;

import controller.Controller;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        Controller controller = new Controller();
        controller.start();
//        LocalDateTime now = LocalDateTime.now();
//        Long year = 5L;
//        LocalDateTime localDateTime = now.plusYears(year);
//        System.out.println("now = " + localDateTime);

    }
}