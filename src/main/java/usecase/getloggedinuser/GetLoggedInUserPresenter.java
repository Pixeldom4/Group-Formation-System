package usecase.getloggedinuser;

import org.jetbrains.annotations.NotNull;

public class GetLoggedInUserPresenter implements GetLoggedInUserOutputBoundary {
    private final LoggedInDataAccessViewModel loggedInDataAccessViewModel;

    public GetLoggedInUserPresenter(LoggedInDataAccessViewModel loggedInDataAccessViewModel) {
        this.loggedInDataAccessViewModel = loggedInDataAccessViewModel;
    }

    @Override
    public void returnLoggedInUser(@NotNull GetLoggedInUserOutputData getLoggedInUserOutputData) {
        loggedInDataAccessViewModel.setLoggedInUser(getLoggedInUserOutputData.getUserId(),
                                                    getLoggedInUserOutputData.getFirstName(),
                                                    getLoggedInUserOutputData.getLastName(),
                                                    getLoggedInUserOutputData.getUserEmail(),
                                                    getLoggedInUserOutputData.getDesiredCompensation(),
                                                    getLoggedInUserOutputData.getTags());
    }

    @Override
    public void notLoggedIn() {
        loggedInDataAccessViewModel.notLoggedIn();
    }
}
