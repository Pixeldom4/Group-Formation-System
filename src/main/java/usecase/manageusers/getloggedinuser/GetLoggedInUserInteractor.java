package usecase.manageusers.getloggedinuser;

import config.DataAccessConfig;
import dataaccess.ILoginUserDetails;
import dataaccess.IUserRepository;
import entities.User;

import java.util.HashSet;

/**
 * Interactor class for getting the logged-in user.
 * Implements the input boundary to handle the retrieval of the logged-in user's information.
 */
public class GetLoggedInUserInteractor implements GetLoggedInUserInputBoundary {
    private final GetLoggedInUserOutputBoundary getLoggedInUserPresenter;
    private final ILoginUserDetails loginUserDetails;
    private final IUserRepository userRepository;

    /**
     * Constructs a GetLoggedInUserInteractor with the specified presenter, login user details, and user repository.
     *
     * @param getLoggedInUserPresenter the presenter to handle output.
     * @param loginUserDetails         the login user details.
     * @param userRepository           the user repository.
     */
    public GetLoggedInUserInteractor(GetLoggedInUserOutputBoundary getLoggedInUserPresenter, ILoginUserDetails loginUserDetails, IUserRepository userRepository) {
        this.getLoggedInUserPresenter = getLoggedInUserPresenter;
        this.loginUserDetails = loginUserDetails;
        this.userRepository = userRepository;
    }

    /**
     * Retrieves the logged-in user's information.
     */
    @Override
    public void getLoggedInUser() {
        if (loginUserDetails.isLoggedIn()) {
            int userId = loginUserDetails.getUserId();
            User user = userRepository.getUserById(userId);
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String userEmail = user.getUserEmail();
            double desiredCompensation = user.getDesiredCompensation();
            HashSet<String> tags = user.getTags();
            getLoggedInUserPresenter.returnLoggedInUser(new GetLoggedInUserOutputData(userId, firstName, lastName, userEmail, desiredCompensation, tags));
        } else {
            getLoggedInUserPresenter.notLoggedIn();
        }
    }
}
