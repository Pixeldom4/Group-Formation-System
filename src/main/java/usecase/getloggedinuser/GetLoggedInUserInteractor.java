package usecase.getloggedinuser;

import dataaccess.DataAccessConfig;
import dataaccess.ILoginUserDetails;
import dataaccess.IUserProjectsRepository;
import dataaccess.IUserRepository;
import entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;

public class GetLoggedInUserInteractor implements GetLoggedInUserInputBoundary {
    private final GetLoggedInUserOutputBoundary getLoggedInUserPresenter;
    private ILoginUserDetails loginUserDetails = DataAccessConfig.getLoginUserDetails();
    private IUserRepository userRepository = DataAccessConfig.getUserRepository();

    public GetLoggedInUserInteractor(GetLoggedInUserOutputBoundary getLoggedInUserPresenter, ILoginUserDetails loginUserDetails, IUserRepository userRepository) {
        this.getLoggedInUserPresenter = getLoggedInUserPresenter;
        this.loginUserDetails = loginUserDetails;
        this.userRepository = userRepository;
    }

    public GetLoggedInUserInteractor(GetLoggedInUserOutputBoundary getLoggedInUserPresenter) {
        this.getLoggedInUserPresenter = getLoggedInUserPresenter;
    }

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
        }
        else {
            getLoggedInUserPresenter.notLoggedIn();
        }
    }
}
