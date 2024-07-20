package usecase.getloggedinuser;

import dataaccess.DataAccessConfig;
import dataaccess.ILoginUserDetails;

import java.util.HashSet;

public class GetLoggedInUserInteractor implements GetLoggedInUserInputBoundary {
    private final GetLoggedInUserOutputBoundary getLoggedInUserPresenter;
    private final ILoginUserDetails loginUserDetails = DataAccessConfig.getLoginUserDetails();

    public GetLoggedInUserInteractor(GetLoggedInUserOutputBoundary getLoggedInUserPresenter) {
        this.getLoggedInUserPresenter = getLoggedInUserPresenter;
    }

    @Override
    public void getLoggedInUser() {
        if (loginUserDetails.isLoggedIn()) {
            int userId = loginUserDetails.getUserId();
            String firstName = loginUserDetails.getFirstName();
            String lastName = loginUserDetails.getLastName();
            String userEmail = loginUserDetails.getUserEmail();
            double desiredCompensation = loginUserDetails.getDesiredCompensation();
            HashSet<String> tags = loginUserDetails.getTags();
            getLoggedInUserPresenter.returnLoggedInUser(new GetLoggedInUserOutputData(userId, firstName, lastName, userEmail, desiredCompensation, tags));
        }
        else {
            getLoggedInUserPresenter.notLoggedIn();
        }
    }
}
