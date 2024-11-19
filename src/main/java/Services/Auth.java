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

        String userSalt = userRepo.getSaltCode(username);

        if (!userRepo.verifyPassword(username, password)) {}

        return false;
    }

    public boolean register(String username, String pass_word) {
        return false;
    }

}
