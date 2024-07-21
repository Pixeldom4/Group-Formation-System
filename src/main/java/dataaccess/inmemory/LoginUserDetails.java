package dataaccess.inmemory;

import dataaccess.ILoginUserDetails;

/**
 * An in-memory implementation of the ILoginUserDetails interface.
 * This class stores the login state and user ID of a logged-in user.
 */
public class LoginUserDetails implements ILoginUserDetails {
    private int userId;
    private boolean isLoggedIn;

    /**
     * Constructs a LoginUserDetails object with the initial login state set to false.
     */
    public LoginUserDetails() {
        this.isLoggedIn = false;
    }

    /**
     * Gets the user ID of the logged-in user.
     *
     * @return the user ID of the logged-in user.
     */
    @Override
    public int getUserId() {
        return userId;
    }

    /**
     * Checks if a user is logged in.
     *
     * @return true if a user is logged in, false otherwise.
     */
    @Override
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    /**
     * Logs out the current user by resetting the user ID and login state.
     */
    @Override
    public void logout() {
        userId = 0;
        this.isLoggedIn = false;
    }

    /**
     * Logs in a user by setting the user ID and changing the login state to true.
     *
     * @param userId the user ID of the user to log in.
     */
    @Override
    public void login(int userId) {
        this.userId = userId;
        this.isLoggedIn = true;
    }
}
