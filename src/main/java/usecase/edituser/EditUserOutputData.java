package usecase.edituser;

import java.util.HashSet;

/**
 * Data structure for storing the output data related to editing a user profile.
 */
public class EditUserOutputData {
    private final int userId;
    private final String firstName;
    private final String lastName;
    private final double desiredCompensation;
    private final HashSet<String> tags;

    /**
     * Constructs an EditUserOutputData object with the specified details.
     *
     * @param userId              the ID of the user.
     * @param firstName           the first name of the user.
     * @param lastName            the last name of the user.
     * @param desiredCompensation the desired compensation of the user.
     * @param tags                the tags for the user.
     */
    public EditUserOutputData(int userId, String firstName, String lastName, double desiredCompensation, HashSet<String> tags) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.desiredCompensation = desiredCompensation;
        this.tags = tags;
    }

    /**
     * Returns the user ID.
     *
     * @return the ID of the user.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Returns the first name of the user.
     *
     * @return the first name of the user.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name of the user.
     *
     * @return the last name of the user.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the desired compensation of the user.
     *
     * @return the desired compensation of the user.
     */
    public double getDesiredCompensation() {
        return desiredCompensation;
    }

    /**
     * Returns the tags for the user.
     *
     * @return the tags for the user.
     */
    public HashSet<String> getTags() {
        return tags;
    }
}
