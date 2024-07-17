package usecase.loginuser;

/**
 * Controller for handling login requests.
 */
public class LoginUserController {
    private final LoginUserInputBoundary loginUserInteractor;

    /**
     * Constructs a LoginUserController with the specified interactor.
     *
     * @param loginUserInteractor the interactor to handle login requests.
     */
    public LoginUserController(LoginUserInputBoundary loginUserInteractor) {
        this.loginUserInteractor = loginUserInteractor;
    }

    /**
     * Calls the interactor to log in a user.
     *
     * @param email    The email of the user.
     * @param password The password of the user.
     */
    public void loginUser(String email, String password) {
        LoginUserInputData inputData = new LoginUserInputData(email, password);
        loginUserInteractor.loginUser(inputData);
    }
}
