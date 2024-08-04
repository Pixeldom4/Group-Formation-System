package usecase.loginuser;

import dataaccess.DataAccessConfig;
import dataaccess.ILoginUserDetails;
import dataaccess.IUserRepository;
import entities.User;

/**
 * Interactor for the login user use case.
 * Handles the business logic for logging in a user.
 */
public class LoginUserInteractor implements LoginUserInputBoundary {
    private final IUserRepository userRepository;
    private final LoginUserOutputBoundary loginUserPresenter;
    private final LoginAuthenticator loginAuthenticator;
    private final ILoginUserDetails loginUserDetails;

    /**
     * Constructs a LoginUserInteractor with the specified repository, presenter, and authenticator.
     *
     * @param userRepository     the repository to interact with the data store.
     * @param loginUserPresenter the presenter to handle the output presentation.
     * @param loginAuthenticator the authenticator to authenticate users.
     * @param loginUserDetails   the login user details repository.
     */
    public LoginUserInteractor(IUserRepository userRepository, LoginUserOutputBoundary loginUserPresenter,
                               LoginAuthenticator loginAuthenticator, ILoginUserDetails loginUserDetails) {
        this.userRepository = userRepository;
        this.loginUserPresenter = loginUserPresenter;
        this.loginAuthenticator = loginAuthenticator;
        this.loginUserDetails = loginUserDetails;
    }

    /**
     * Logs in a user with the provided input data.
     *
     * @param inputData the input data required to log in a user.
     */
    @Override
    public void loginUser(LoginUserInputData inputData) {
        if (loginAuthenticator.authenticate(inputData.getEmail(), inputData.getPassword())) {
            User user = userRepository.getUserByEmail(inputData.getEmail());
            LoginUserOutputData outputData = new LoginUserOutputData(
                    user.getUserId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getUserEmail(),
                    user.getDesiredCompensation(),
                    user.getTags(),
                    true
            );
            loginUserDetails.login(user.getUserId());
            loginUserPresenter.prepareSuccessView(outputData);
        } else {
            loginUserPresenter.prepareFailView("Invalid email or password.");
        }
    }
}
