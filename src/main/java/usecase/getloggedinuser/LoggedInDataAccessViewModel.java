package usecase.getloggedinuser;

import java.util.HashSet;

/**
 * Should be implemented by view models that access the logged-in user's data.
 */
public interface LoggedInDataAccessViewModel {

    /**
     * Sets the logged-in user data in the view model.
     *
     * @param userId             the ID of the user.
     * @param firstName          the first name of the user.
     * @param lastName           the last name of the user.
     * @param userEmail          the email of the user.
     * @param desiredCompensation the desired compensation of the user.
     * @param tags               the tags associated with the user.
     */
    void setLoggedInUser(int userId, String firstName, String lastName, String userEmail, double desiredCompensation, HashSet<String> tags);

    /**
     * Called when the user is not logged in.
     */
    void notLoggedIn();
}
