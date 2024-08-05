package usecase.manageusers.getloggedinuser;

/**
 * Output boundary interface for getting the logged-in user.
 * Defines methods to prepare success and failure views.
 */
public interface GetLoggedInUserOutputBoundary {

    /**
     * Returns the logged-in user's data.
     *
     * @param getLoggedInUserOutputData the logged-in user's data.
     */
    void returnLoggedInUser(GetLoggedInUserOutputData getLoggedInUserOutputData);

    /**
     * Calls when the user is not logged in.
     */
    void notLoggedIn();
}
