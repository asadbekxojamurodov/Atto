package service;

import RandomUtils.RandomUtil;
import dto.CardDto;
import dto.ProfileDto;
import enums.Status;
import repository.CardRepository;
import repository.ProfileRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class CardService {
    CardRepository cardRepository = new CardRepository();
    ProfileRepository profileRepository = new ProfileRepository();
    String cardNumber;
    long smallest = 1000_0000_0000_0000L;
    long biggest = 9999_9999_9999_9999L;
    boolean bool;

    public boolean createCard() {
        CardDto card = new CardDto();
        cardNumber = String.valueOf(RandomUtil.getRandomNumber(smallest, biggest));
        card.setCardNumber(cardNumber);
        card.setExpireDate(LocalDate.now());
        card.setBalance(0.0);
        card.setStatus(Status.NO_ACTIVE);
        card.setCreatedDate(LocalDateTime.now());

        return cardRepository.createCard(card);
    }

    public void getCardList() {
        List<CardDto> allCard = cardRepository.getAllCard();
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
        String cardNumber;
        List<CardDto> allNoActiveCardList = cardRepository.getAllNoActiveCardList();

        for (CardDto cardDto : allNoActiveCardList) {

            cardDto.setPhone(profileDto.getPhone());
            cardDto.setStatus(Status.ACTIVE);
            cardNumber = cardDto.getCardNumber();
            return cardRepository.addCard(cardDto, cardNumber);
        }
        return false;
    }

    public boolean updateCard(CardDto cardDto) {
        List<CardDto> cardDtoList = cardRepository.getAllCard();
        for (CardDto dto : cardDtoList) {
            if (cardDto.getCardNumber().equals(dto.getCardNumber())) {
                cardDto.setExpireDate(LocalDate.now().plusYears(5l));
                return bool = cardRepository.update(cardDto);
            }
        }
        return false;
    }

    public boolean deleteCard(CardDto cardDto) {

        List<CardDto> cardDtoList = cardRepository.getAllCard();
        for (CardDto dto : cardDtoList) {
            if (dto.getCardNumber().equals(cardDto.getCardNumber())) {
                cardDto.setStatus(Status.BLOCKED);
                cardDto.setCardNumber(dto.getCardNumber());
               return cardRepository.deleteCard(cardDto);
            }
        }
        return false;
    }

    public void getMyCardList(ProfileDto profileDto) {
        List<CardDto> allCard = cardRepository.getAllMyCard(profileDto.getPhone());
        for (CardDto cardDto : allCard) {
            System.out.println("CARD NUMBER < "+cardDto.getCardNumber() + " > EXPIRE DATE < " + cardDto.getExpireDate() + " > BALANCE < " + cardDto.getBalance()+" > status < " + cardDto.getStatus()+" >" );
        }
    }


    public boolean changeCardStatus(String cardNumber,String phone) {

        List<CardDto> allMyCard = cardRepository.getAllMyCard(phone);
        for (CardDto cardDto : allMyCard) {
            if (cardDto.getCardNumber().equals(cardNumber)){
                return cardRepository.changeCardStatus(cardNumber);

            }
        }
        return false;
    }

}





