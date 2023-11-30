package service;

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
    boolean bool;

    public boolean createCard(CardDto card) {

        card.setStatus(Status.NO_ACTIVE);
        card.setCreatedDate(LocalDateTime.now());
        cardRepository.createCard(card);
        return false;
    }

    public void getCardList() {
        List<CardDto> allCard = cardRepository.getAllCard();
        for (CardDto cardDto : allCard) {
            System.out.println(cardDto);
        }

    }

    public boolean addCard(ProfileDto profileDto) {
        boolean bool = false;
        List<ProfileDto> allProfile = profileRepository.getAllProfile();
        for (ProfileDto dto : allProfile) {
            if (profileDto.getName().equals(dto.getName()) && profileDto.getSurname().equals(dto.getSurname()) && profileDto.getPhone().equals(dto.getPhone())) {
                 bool = setCardForUser(profileDto);
                return bool;
            }
        }
        return false;
    }

    public boolean setCardForUser(ProfileDto profileDto) {
        boolean bool = false;
        List<CardDto> cardDtoList = cardRepository.getAllCard();
        for (CardDto cardDto : cardDtoList) {
            if (cardDto.getStatus().equals(Status.NO_ACTIVE)) {
                cardDto.setPhone(profileDto.getPhone());
                cardDto.setExpireDate(LocalDate.now().plusYears(3l));
                cardDto.setStatus(Status.ACTIVE);

                bool = cardRepository.addCard(cardDto);
                return bool;
            }
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
        Boolean bool = null;
        List<CardDto> cardDtoList = cardRepository.getAllCard();
        for (CardDto dto : cardDtoList) {
            if (dto.getCardNumber().equals(cardDto.getCardNumber())) {
//                cardDto.setCardNumber(dto.getCardNumber());
                cardDto.setStatus(Status.BLOCKED);
                bool = cardRepository.deleteCard(cardDto);
                break;
            }
        }
        if (Boolean.TRUE.equals(bool)) {
            return true;
        }
        return false;
    }
}





