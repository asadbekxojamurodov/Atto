package utils;

import controller.CardController;
import controller.Controller;
import dto.CardDto;
import repository.CardRepository;
import service.CardService;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        Controller controller = new Controller();
        controller.start();

//        LocalDateTime now = LocalDateTime.now();
//        Long year = 5L;
//        LocalDateTime localDateTime = now.plusYears(year);
//        System.out.println("now = " + localDateTime);

//        ProfileRepository profileRepository = new ProfileRepository();
//        List<ProfileDto> allProfile = profileRepository.getAllProfile();
//        for (ProfileDto profileDto : allProfile) {
//            System.out.println(profileDto);
//        }
//
//        CardRepository cardRepository = new CardRepository();
//        List<CardDto> allNoActiveCardList = cardRepository.getAllNoActiveCardList();
//        System.out.println(allNoActiveCardList);

    }
}