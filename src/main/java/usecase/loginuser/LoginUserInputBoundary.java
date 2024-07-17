package usecase.loginuser;

/**
 * The input boundary for the login user use case.
 */
public interface LoginUserInputBoundary {
    /**
     * Logs in a user with the provided input data.
     *
     * @param inputData the input data required to log in a user.
     */
    void loginUser(LoginUserInputData inputData);
}
