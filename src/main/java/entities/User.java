package entities;

import java.util.HashSet;

/**
 * Represents a user entity with details about the user.
 */
public class User implements UserInterface {
    private int userId;
    private String firstName;
    private String lastName;
    private String userEmail;
    private HashSet<String> tags;
    private double desiredCompensation;

    /**
     * Constructs a User object with the specified details.
     *
     * @param userId the ID of the user.
     * @param firstName the first name of the user.
     * @param lastName the last name of the user.
     * @param userEmail the email address of the user.
     * @param tags a set of tags associated with the user.
     * @param desiredCompensation the desired compensation of the user.
     */
    public User(int userId, String firstName, String lastName, String userEmail, HashSet<String> tags, double desiredCompensation) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userEmail = userEmail;
        this.tags = tags;
        this.desiredCompensation = desiredCompensation;
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
     * Sets the user ID.
     *
     * @param userId the user ID.
     */
    public void setUserId(int userId) {
        this.userId = userId;
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
     * Sets the first name of the user.
     *
     * @param firstName the first name of the user.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
     * Sets the last name of the user.
     *
     * @param lastName the last name of the user.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
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
     * Sets the email address of the user.
     *
     * @param userEmail the email address of the user.
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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
     * Sets the tags associated with the user.
     *
     * @param tags a set of tags associated with the user.
     */
    public void setTags(HashSet<String> tags) {
        this.tags = tags;
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
     * Sets the desired compensation of the user.
     *
     * @param desiredCompensation the desired compensation of the user.
     */
    public void setDesiredCompensation(double desiredCompensation) {
        this.desiredCompensation = desiredCompensation;
    }
}
