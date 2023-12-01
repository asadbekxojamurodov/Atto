package service;

import dto.ProfileDto;
import repository.ProfileRepository;

import java.util.LinkedList;
import java.util.List;

public class ProfileService {
    ProfileRepository profileRepository = new ProfileRepository();
    List<ProfileDto> profileDtoList = new LinkedList<>();

    public ProfileDto login(ProfileDto profile) {

        return profileRepository.login(profile);
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
