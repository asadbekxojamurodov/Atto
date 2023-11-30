package utils;

import RandomUtils.RandomUtil;
import controller.Controller;
import dto.CardDto;
import dto.ProfileDto;
import repository.CardRepository;
import repository.ProfileRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
//        List<CardDto> allCard = cardRepository.getAllCard();

//        long smallest = 1000_0000_0000_0000L;
//        long biggest =  9999_9999_9999_9999L;
//
//        long randomNumber = RandomUtil.getRandomNumber(smallest, biggest);
//        System.out.println("randomNumber = " + randomNumber);


    }
}