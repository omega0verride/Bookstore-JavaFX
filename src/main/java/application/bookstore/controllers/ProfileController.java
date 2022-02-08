package application.bookstore.controllers;

import application.bookstore.models.Role;
import application.bookstore.views.ProfileView;

public class ProfileController {
    private final ProfileView profileView;

    public ProfileController(ProfileView profileView) {
        this.profileView = profileView;
        Role currentRole = profileView.getCurrentUser().getRole();

    }


}
