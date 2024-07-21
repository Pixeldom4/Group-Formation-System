package usecase.getloggedinuser;

import java.util.HashSet;

/**
 * This is an immutable data structure responsible for storing the output
 * data related to getting the logged-in user.
 */
public class GetLoggedInUserOutputData {

    private final int userId;
    private final String firstName;
    private final String lastName;
    private final String userEmail;
    private final double desiredCompensation;
    private final HashSet<String> tags;

    /**
     * Constructs a GetLoggedInUserOutputData object with the specified details.
     *
     * @param userId             the ID of the user.
     * @param firstName          the first name of the user.
     * @param lastName           the last name of the user.
     * @param userEmail          the email of the user.
     * @param desiredCompensation the desired compensation of the user.
     * @param tags               the tags associated with the user.
     */
    public GetLoggedInUserOutputData(int userId, String firstName, String lastName, String userEmail, double desiredCompensation, HashSet<String> tags) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userEmail = userEmail;
        this.desiredCompensation = desiredCompensation;
        this.tags = tags;
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
        return firstName;
    }

    /**
     * Gets the last name of the user.
     *
     * @return the last name of the user.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the email of the user.
     *
     * @return the email of the user.
     */
    public String getUserEmail() {
        return userEmail;
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
     * Gets the tags associated with the user.
     *
     * @return the tags associated with the user.
     */
    public HashSet<String> getTags() {
        return tags;
    }
}
