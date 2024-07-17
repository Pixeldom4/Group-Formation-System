package usecase.loginuser;

/**
 * This is an immutable data structure responsible for storing the input
 * data related to logging in a user.
 */
public class LoginUserInputData {
    private final String email;
    private final String password;

    /**
     * Constructs a LoginUserInputData object with the specified details.
     *
     * @param email    the email of the user.
     * @param password the password of the user.
     */
    public LoginUserInputData(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Returns the email of the user.
     *
     * @return the user's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the password of the user.
     *
     * @return the user's password.
     */
    public String getPassword() {
        return password;
    }
}
