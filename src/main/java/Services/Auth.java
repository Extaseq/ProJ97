package Services;

import Database.UserRepository;

public class Auth {
    private final UserRepository userRepo;

    public Auth() {
        this.userRepo = new UserRepository();
    }

    public boolean login(String username, String password) {
        if (!userRepo.userExists(username)) {
            return false;
        }

        String[] AuthInfo = userRepo.getAuthInformation(username);
        return PasswordUtil.verifyPassword(password, AuthInfo[0], AuthInfo[1]);
    }

    public boolean register(String username, String pass_word) {
        String generatedSalt = PasswordUtil.generateSalt();
        String hashedPassword = PasswordUtil.hashPassword(pass_word, generatedSalt);
        String lastMemberAdded = String.valueOf(userRepo.getLastUserId());
        return userRepo.addNewUser(lastMemberAdded, username, hashedPassword, generatedSalt) > 0;
    }

}
