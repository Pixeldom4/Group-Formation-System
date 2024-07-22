package usecase.edituser;

import java.util.HashSet;

/**
 * This is an immutable data structure responsible for storing the input
 * data related to edit a user profile.
 */
public class EditUserInputData {
    private String firstName;
    private String lastName;
    private double desiredCompensation;
    private int userId;
    private HashSet<String> tags;

    /**
     * Edits a CreateProjectInputData object with the specified details.
     *
     * @param userId              the ID of the user.
     * @param firstName           the first name of the user.
     * @param lastName            the last name of the user.
     * @param desiredCompensation the desired compensation of the user.
     * @param tags                the tags for the user.
     */
    public EditUserInputData(int userId, String firstName, String lastName, double desiredCompensation, HashSet<String> tags) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.desiredCompensation = desiredCompensation;
        this.tags = tags;
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
     * Returns the user ID.
     *
     * @return the ID of the user.
     */
    public int getUserId() {
        return userId;
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
