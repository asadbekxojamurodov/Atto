package service;

import dto.ProfileDto;
import enums.Status;
import repository.ProfileRepository;

import java.util.LinkedList;
import java.util.List;

public class ProfileService {
    ProfileRepository profileRepository = new ProfileRepository();

    public void getAllProfileList() {
        List<ProfileDto> profileDtoList = profileRepository.getAllProfile();
        if (profileDtoList.isEmpty()) {
            System.out.println("profile list is empty");
            return;
        }
        for (ProfileDto profileDto : profileDtoList) {
            System.out.println(profileDto);
        }
    }


    public void changeProfileStatus(String phone) {
        ProfileDto profile = profileRepository.getProfileByPhone(phone);
        if (profile == null) {
            System.out.println("profile not found");
            return;
        }

        if (profile.getStatus().equals(Status.ACTIVE)) {
            profileRepository.changeProfileStatus(profile, Status.BLOCKED);

        } else if (profile.getStatus().equals(Status.BLOCKED)) {
            profileRepository.changeProfileStatus(profile, Status.ACTIVE);
        }
    }


}


