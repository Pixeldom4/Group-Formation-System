package dataaccess;

import java.util.HashSet;

public interface ILoginUserDetails {
    int getUserId();
    String getUserEmail();
    String getFirstName();
    String getLastName();
    double getDesiredCompensation();
    HashSet<String> getTags();
    boolean isLoggedIn();

    /**
     * Logout the user and reset the details.
     */
    void logout();

    /**
     * Login the user with the specified details.
     * @param userId user id
     * @param userEmail user email
     * @param firstName first name
     * @param lastName last name
     * @param desiredCompensation desired compensation
     * @param tags user tags
     */
    void login(int userId, String userEmail, String firstName, String lastName, double desiredCompensation, HashSet<String> tags);
}
