package usecase.getloggedinuser;

import java.util.HashSet;

/**
 * Should be implemented by view models that access the logged-in user's data
 */
public interface LoggedInDataAccessViewModel {

    /**
     * Sets the logged-in user data in the view model
     * @param userId user id
     * @param firstName first name
     * @param lastName last name
     * @param userEmail user email
     * @param desiredCompensation desired compensation
     * @param tags user tags
     */
    void setLoggedInUser(int userId, String firstName, String lastName, String userEmail, double desiredCompensation, HashSet<String> tags);

    /**
     * Calls when the user is not logged in
     */
    void notLoggedIn();
}
