package usecase.loginuser;

/**
 * Interface for authenticating a user during the login process.
 */
public interface LoginAuthenticator {
    /**
     * Authenticates a user by their email and password.
     *
     * @param email    the email of the user.
     * @param password the password of the user.
     * @return true if authentication is successful, otherwise false.
     */
    boolean authenticate(String email, String password);
}
