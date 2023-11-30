package service;

import dto.CardDto;
import enums.Status;
import repository.CardRepository;

import java.time.LocalDateTime;
import java.util.List;

public class CardService {
    CardRepository cardRepository = new CardRepository();

    public void createCard(CardDto card) {

        card.setStatus(Status.NO_ACTIVE);
        card.setCreatedDate(LocalDateTime.now());
        card.setPhone(null);
        card.setBalance(0.0);
       cardRepository.createCard(card);
    }

    public void getCardList() {
    List<CardDto> cardList = cardRepository.getAllCard();
        for (CardDto cardDto : cardList) {
            System.out.println(cardDto);
        }
    }
}
