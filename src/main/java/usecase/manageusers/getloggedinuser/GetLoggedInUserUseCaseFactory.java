package usecase.manageusers.getloggedinuser;

import config.DataAccessConfig;
import dataaccess.ILoginUserDetails;
import dataaccess.IUserRepository;

/**
 * Factory class for creating instances of the GetLoggedInUser use case.
 */
public class GetLoggedInUserUseCaseFactory {
    private static final ILoginUserDetails loginUserDetails = DataAccessConfig.getLoginUserDetails();
    private static final IUserRepository userRepository = DataAccessConfig.getUserRepository();

    // Private constructor to prevent instantiation
    private GetLoggedInUserUseCaseFactory() {
    }

    /**
     * Creates a new GetLoggedInUserController with the given LoggedInDataAccessViewModel.
     *
     * @param loggedInDataAccessViewModel the LoggedInDataAccessViewModel.
     * @return a new GetLoggedInUserController.
     */
    public static GetLoggedInUserController create(LoggedInDataAccessViewModel loggedInDataAccessViewModel) {
        GetLoggedInUserOutputBoundary presenter = new GetLoggedInUserPresenter(loggedInDataAccessViewModel);
        GetLoggedInUserInputBoundary interactor = new GetLoggedInUserInteractor(presenter, loginUserDetails, userRepository);
        return new GetLoggedInUserController(interactor);
    }
}
