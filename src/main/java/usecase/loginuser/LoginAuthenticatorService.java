package usecase.loginuser;

import dataaccess.IUserRepository;
import usecase.PasswordHasher;

/**
 * Service class for authenticating a user during the login process.
 */
public class LoginAuthenticatorService implements LoginAuthenticator {
    private final IUserRepository userRepository;
    private final PasswordHasher passwordHasher;

    /**
     * Constructs a LoginAuthenticatorService with the specified repository and password hasher.
     *
     * @param userRepository the repository to interact with the data store.
     * @param passwordHasher the password hasher to verify passwords.
     */
    public LoginAuthenticatorService(IUserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    /**
     * Authenticates a user by their email and password.
     *
     * @param email    the email of the user.
     * @param password the password of the user.
     * @return true if authentication is successful, otherwise false.
     */
    @Override
    public boolean authenticate(String email, String password) {
        String hashedPassword = userRepository.getPasswordByEmail(email);
        return passwordHasher.checkPassword(password, hashedPassword);
    }
}
