package com.nichga.proj97.Services;

import com.nichga.proj97.Database.UserRepository;

/**
 * The Auth class provides authentication and user registration functionalities.
 * It interacts with the UserRepository to manage user login and registration operations.
 */
public class Auth {
    private final UserRepository userRepo;

    public Auth() {
        this.userRepo = new UserRepository();
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
    public boolean register(String username, String pass_word) {
        if (userRepo.userExists(username)) {
            return false;
        }
        String generatedSalt = PasswordUtil.generateSalt();
        String hashedPassword = PasswordUtil.hashPassword(pass_word, generatedSalt);
        String lastMemberAdded = String.valueOf(userRepo.getLastUserId() + 1);
        return userRepo.addNewUser(lastMemberAdded, username, hashedPassword, generatedSalt) > 0;
    }
}
