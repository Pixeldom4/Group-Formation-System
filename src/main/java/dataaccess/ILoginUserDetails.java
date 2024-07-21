package dataaccess;

/**
 * Interface for managing login user details.
 * Provides methods to get login status, user ID, and to login/logout the user.
 */
public interface ILoginUserDetails {
    /**
     * Gets the user ID of the logged-in user.
     *
     * @return the user ID of the logged-in user.
     */
    int getUserId();

    /**
     * Checks if a user is logged in.
     *
     * @return true if a user is logged in, false otherwise.
     */
    boolean isLoggedIn();

    /**
     * Logs out the user and resets the login details.
     */
    void logout();

    /**
     * Logs in a user with the specified user ID.
     *
     * @param userId the user ID of the user to log in.
     */
    void login(int userId);
}
