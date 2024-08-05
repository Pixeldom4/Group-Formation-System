package usecase.manageusers.getusers;

import java.util.HashSet;

/**
 * Data transfer object for user data.
 */
public class UserData {
    private final int userId;
    private final String firstName;
    private final String lastName;
    private final String userEmail;
    private final HashSet<String> tags;
    private final double desiredCompensation;
    private final boolean isOwner;

    /**
     * Constructs a UserData object with the specified details.
     *
     * @param userId the ID of the user.
     * @param firstName the first name of the user.
     * @param lastName the last name of the user.
     * @param userEmail the email address of the user.
     * @param tags a set of tags associated with the user.
     * @param desiredCompensation the desired compensation of the user.
     * @param isOwner flag indicating if the user is the owner.
     */
    public UserData(int userId, String firstName, String lastName, String userEmail, HashSet<String> tags, double desiredCompensation, boolean isOwner) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userEmail = userEmail;
        this.tags = tags;
        this.desiredCompensation = desiredCompensation;
        this.isOwner = isOwner;
    }

    /**
     * Gets the user ID.
     *
     * @return the user ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Gets the first name of the user.
     *
     * @return the first name of the user.
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Gets the last name of the user.
     *
     * @return the last name of the user.
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Gets the email address of the user.
     *
     * @return the email address of the user.
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * Gets the tags associated with the user.
     *
     * @return a set of tags associated with the user.
     */
    public HashSet<String> getTags() {
        return tags;
    }

    /**
     * Gets the desired compensation of the user.
     *
     * @return the desired compensation of the user.
     */
    public double getDesiredCompensation() {
        return desiredCompensation;
    }

    /**
     * Checks if the user is the owner.
     *
     * @return true if the user is the owner, false otherwise.
     */
    public boolean isOwner() {
        return isOwner;
    }
}
