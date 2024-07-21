package usecase.getloggedinuser;

import org.jetbrains.annotations.NotNull;

/**
 * Presenter class for getting the logged-in user.
 * Implements the output boundary to prepare views for retrieving the logged-in user's information.
 */
public class GetLoggedInUserPresenter implements GetLoggedInUserOutputBoundary {
    private final LoggedInDataAccessViewModel loggedInDataAccessViewModel;

    /**
     * Constructs a GetLoggedInUserPresenter with the specified view model.
     *
     * @param loggedInDataAccessViewModel the view model to update with the logged-in user's information.
     */
    public GetLoggedInUserPresenter(LoggedInDataAccessViewModel loggedInDataAccessViewModel) {
        this.loggedInDataAccessViewModel = loggedInDataAccessViewModel;
    }

    /**
     * Returns the logged-in user's data.
     *
     * @param getLoggedInUserOutputData the logged-in user's data.
     */
    @Override
    public void returnLoggedInUser(@NotNull GetLoggedInUserOutputData getLoggedInUserOutputData) {
        loggedInDataAccessViewModel.setLoggedInUser(getLoggedInUserOutputData.getUserId(),
                getLoggedInUserOutputData.getFirstName(),
                getLoggedInUserOutputData.getLastName(),
                getLoggedInUserOutputData.getUserEmail(),
                getLoggedInUserOutputData.getDesiredCompensation(),
                getLoggedInUserOutputData.getTags());
    }

    /**
     * Calls when the user is not logged in.
     */
    @Override
    public void notLoggedIn() {
        loggedInDataAccessViewModel.notLoggedIn();
    }
}
