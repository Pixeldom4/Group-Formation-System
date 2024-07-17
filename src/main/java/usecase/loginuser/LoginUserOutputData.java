package usecase.loginuser;

import java.util.HashSet;

/**
 * This is an immutable data structure responsible for storing the output
 * data related to logging in a user.
 */
public class LoginUserOutputData {
    private final int userId;
    private final String firstName;
    private final String lastName;
    private final String userEmail;
    private final double desiredCompensation;
    private final HashSet<String> tags;
    private final boolean success;

    /**
     * Constructs a LoginUserOutputData object with the specified details.
     *
     * @param userId              the ID of the logged-in user.
     * @param firstName           the first name of the user.
     * @param lastName            the last name of the user.
     * @param userEmail           the email of the user.
     * @param desiredCompensation the desired compensation of the user.
     * @param tags                a set of tags associated with the user.
     * @param success             whether the login was successful.
     */
    public LoginUserOutputData(int userId, String firstName, String lastName, String userEmail, double desiredCompensation, HashSet<String> tags, boolean success) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userEmail = userEmail;
        this.desiredCompensation = desiredCompensation;
        this.tags = tags;
        this.success = success;
    }

    /**
     * Returns the ID of the logged-in user.
     *
     * @return the user ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Returns the first name of the user.
     *
     * @return the user's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name of the user.
     *
     * @return the user's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the email of the user.
     *
     * @return the user's email.
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * Returns the desired compensation of the user.
     *
     * @return the user's desired compensation.
     */
    public double getDesiredCompensation() {
        return desiredCompensation;
    }

    /**
     * Returns the set of tags associated with the user.
     *
     * @return the set of tags.
     */
    public HashSet<String> getTags() {
        return tags;
    }

    /**
     * Returns whether the login was successful.
     *
     * @return true if the login was successful, otherwise false.
     */
    public boolean isSuccess() {
        return success;
    }
}
