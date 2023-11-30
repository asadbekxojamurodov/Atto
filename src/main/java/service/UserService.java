package service;

import dto.ProfileDto;
import enums.ProfileRole;
import enums.Status;
import repository.ProfileRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class UserService {
    ProfileRepository profileRepository = new ProfileRepository();
    List<ProfileDto> profileDtoList = new LinkedList<>();

    public ProfileDto login(ProfileDto profile) {

        ProfileDto profileDto = profileRepository.login(profile);
        return profileDto;
    }


    public boolean registration(ProfileDto profile) {

        boolean profileDto = profileRepository.registration(profile);

        return profileDto;
    }

    public void getAllProfileList() {
       profileDtoList = profileRepository.getAllProfile();
        for (ProfileDto profileDto : profileDtoList) {
            System.out.println(profileDto);
        }
    }


}
