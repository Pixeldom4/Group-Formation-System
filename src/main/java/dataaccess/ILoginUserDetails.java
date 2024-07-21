package dataaccess;

import java.util.HashSet;

public interface ILoginUserDetails {
    int getUserId();
    boolean isLoggedIn();

    /**
     * Logout the user and reset the details.
     */
    void logout();

    /**
     * Login the user with the specified details.
     * @param userId user id
     */
    void login(int userId);
}
