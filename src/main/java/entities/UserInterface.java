package entities;

import java.util.HashSet;

/**
 * Interface for the User entity.
 */
public interface UserInterface {

    /**
     * Gets the user ID.
     *
     * @return the user ID.
     */
    int getUserId();

    /**
     * Gets the first name of the user.
     *
     * @return the first name of the user.
     */
    String getFirstName();

    /**
     * Gets the last name of the user.
     *
     * @return the last name of the user.
     */
    String getLastName();

    /**
     * Gets the email address of the user.
     *
     * @return the email address of the user.
     */
    String getUserEmail();

    /**
     * Gets the tags associated with the user.
     *
     * @return a set of tags associated with the user.
     */
    HashSet<String> getTags();

    /**
     * Gets the desired compensation of the user.
     *
     * @return the desired compensation of the user.
     */
    double getDesiredCompensation();

    /**
     * Sets the user ID.
     *
     * @param userId the user ID.
     */
    void setUserId(int userId);

    /**
     * Sets the first name of the user.
     *
     * @param firstName the first name of the user.
     */
    void setFirstName(String firstName);

    /**
     * Sets the last name of the user.
     *
     * @param lastName the last name of the user.
     */
    void setLastName(String lastName);

    /**
     * Sets the email address of the user.
     *
     * @param userEmail the email address of the user.
     */
    void setUserEmail(String userEmail);

    /**
     * Sets the tags associated with the user.
     *
     * @param tags a set of tags associated with the user.
     */
    void setTags(HashSet<String> tags);

    /**
     * Sets the desired compensation of the user.
     *
     * @param desiredCompensation the desired compensation of the user.
     */
    void setDesiredCompensation(double desiredCompensation);
}
