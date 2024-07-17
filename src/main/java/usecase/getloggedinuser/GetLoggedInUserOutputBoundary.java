package usecase.getloggedinuser;

public interface GetLoggedInUserOutputBoundary {

    /**
     * Returns the logged-in user's data
     * @param getLoggedInUserOutputData the logged-in user's data
     */
    void returnLoggedInUser(GetLoggedInUserOutputData getLoggedInUserOutputData);

    /**
     * Calls when the user is not logged in
     */
    void notLoggedIn();
}
