package usecase.getloggedinuser;

import dataaccess.DAOImplementationConfig;
import dataaccess.ILoginUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;

public class GetLoggedInUserInteractor implements GetLoggedInUserInputBoundary {
    private GetLoggedInUserOutputBoundary getLoggedInUserPresenter;
    private ILoginUserDetails loginUserDetails = DAOImplementationConfig.getLoginUserDetails();

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
