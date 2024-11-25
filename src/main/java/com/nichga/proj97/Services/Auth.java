package com.nichga.proj97.Services;

import com.nichga.proj97.Database.MemberRepository;
import com.nichga.proj97.Database.UserRepository;
import com.nichga.proj97.Users;
import javafx.scene.control.Alert;

/**
 * The Auth class provides authentication and user registration functionalities.
 * It interacts with the UserRepository to manage user login and registration operations.
 */
public class Auth {
    private final UserRepository userRepo;
    private final MemberRepository memberRepo;

    public Auth() {
        this.userRepo = new UserRepository();
        memberRepo = new MemberRepository();
    }

    /**
     * Attempts to log in a user by verifying the provided username and password.
     *
     * @param username The username of the user attempting to log in.
     * @param password The password provided by the user.
     * @return {@code true} if the login is successful, {@code false} otherwise.
     */
    public boolean login(String username, String password) {
        if (!userRepo.userExists(username)) {
            return false;
        }
        String[] AuthInfo = userRepo.getAuthInformation(username);
        return PasswordUtil.verifyPassword(password, AuthInfo[0], AuthInfo[1]);
    }

    /**
     * Registers a new user with the given username and password.
     * The password is hashed and stored securely along with a generated salt.
     *
     * @param username  The username of the new user.
     * @param pass_word The plain-text password provided by the new user.
     * @return {@code true} if the registration is successful, {@code false} otherwise.
     */
    public boolean register(String username, String pass_word, String fullname) {
        if (userRepo.userExists(username)) {
            return false;
        }
        String generatedSalt = PasswordUtil.generateSalt();
        String hashedPassword = PasswordUtil.hashPassword(pass_word, generatedSalt);
        String lastMemberAdded = String.valueOf(userRepo.getLastUserId() + 1);
        return userRepo.addNewUser(lastMemberAdded, username, hashedPassword, generatedSalt) > 0
                && memberRepo.addNewMember(lastMemberAdded, fullname) > 0;
    }

    public boolean changePassword(Users user, String oldPass, String newPass) {
        String[] AuthInfo = userRepo.getAuthInformation(user.getUsername());
        System.out.println(user.getUsername());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(PasswordUtil.verifyPassword(oldPass, AuthInfo[0], AuthInfo[1])) {
            String newPasswordHash = PasswordUtil.hashPassword(newPass, AuthInfo[1]);
            userRepo.changePassword(newPasswordHash, user.getUsername());
            alert.setContentText("Change Password Success!");
            alert.showAndWait();
            return true;
        } else {
            alert.setContentText("Wrong Password!");
            alert.showAndWait();
            return false;
        }
    }
}
