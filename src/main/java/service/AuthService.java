package service;

import controller.AdminController;
import controller.ProfileController;
import dto.ProfileDto;
import enums.ProfileRole;
import enums.Status;
import repository.ProfileRepository;

public class AuthService {
    private ProfileRepository profileRepository;
    ProfileController profileController = new ProfileController();

    public AuthService() {
        profileRepository = new ProfileRepository();
    }


    public void login(String phone, String password) {

        ProfileDto profile = profileRepository.getProfileByPhoneAndPassword(phone, password);

        if (profile == null) {
            System.out.println("Phone or Password incorrect");
            return;
        }

        if (!profile.getStatus().equals(Status.ACTIVE)) {
            System.out.println("You not allowed.MF");
            return;
        }

        if (profile.getProfileRole().equals(ProfileRole.ADMIN)) {
            AdminController adminController = new AdminController();
            adminController.start();

        } else if (profile.getProfileRole().equals(ProfileRole.USER)) {

            profileController.start(profile);

        } else {
            System.out.println("You don't have any role.");
        }

    }

    public void registration(ProfileDto profile) {
        ProfileDto exist = profileRepository.getProfileByPhone(profile.getPhone());
        if (exist != null) {
            System.out.println("profile exist");
            return;
        }
        profile.setProfileRole(ProfileRole.USER);
        profile.setStatus(Status.ACTIVE);


        if (profileRepository.registration(profile) != 0) {
            System.out.println("successfully registered");
        }
        profileController.start(profile);

    }
}
