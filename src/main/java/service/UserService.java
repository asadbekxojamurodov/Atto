package service;

import dto.ProfileDto;
import enums.ProfileRole;
import enums.Status;
import repository.ProfileRepository;

import java.time.LocalDateTime;

public class UserService {
    ProfileRepository profileRepository = new ProfileRepository();

    public ProfileDto login(ProfileDto profile) {

        ProfileDto profileDto = profileRepository.login(profile);
        return profileDto;
    }


    public boolean registration(ProfileDto profile) {

        boolean profileDto = profileRepository.registration(profile);

        return profileDto;
    }
}
