package db;

import dto.CardDto;
import dto.ProfileDto;
import enums.ProfileRole;
import enums.Status;
import repository.CardRepository;
import repository.ProfileRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class InitDatabase {
    public static void adminInit() {

        ProfileDto profile = new ProfileDto();
        profile.setName("Admin");
        profile.setSurname("Adminjon");
        profile.setPhone("123");
        profile.setPassword("123");
        profile.setCreatedDate(LocalDateTime.now());
        profile.setStatus(Status.ACTIVE);
        profile.setProfileRole(ProfileRole.ADMIN);


        ProfileRepository profileRepository = new ProfileRepository();

        ProfileDto profile1 = profileRepository.getProfileByPhone(profile.getPhone());
        if (profile1 != null) {
            return;
        }
        profileRepository.registration(profile);

    }

    public static void addCompanyCard() {
        CardDto card = new CardDto();
        card.setCardNumber("5555");
        card.setExpireDate(LocalDate.of(2025, 12, 01));

        card.setPhone("123");
        card.setBalance(0d);
        card.setCreatedDate(LocalDateTime.now());
        card.setStatus(Status.ACTIVE);

        CardRepository cardRepository = new CardRepository();
        CardDto exists = cardRepository.getCardByNumber(card.getCardNumber());

        if (exists != null) {
            return;
        }
        cardRepository.saveCompanyCard(card);
    }

}
