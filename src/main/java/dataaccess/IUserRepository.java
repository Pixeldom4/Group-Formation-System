package dataaccess;

import entities.User;

import java.util.HashSet;

/**
 * Interface for user repository operations.
 * Defines methods for managing users in the database.
 */
public interface IUserRepository {

    /**
     * Creates/Registers a user in the database. Note that all validation should be done
     * prior to this method in accordance with clean architecture. Particularly, validation
     * should be done in the service class / (data access object ?).
     *
     * @param email the user's Email address.
     * @param firstName the user's first name.
     * @param lastName the user's last name.
     * @param password the user's password.
     * @return a User object corresponding to the created user. Otherwise, null.
     */
    User createUser(String email, String firstName, String lastName, HashSet<String> tags, double desiredCompensation, String password);

    /**
     * Gets the User object corresponding to the user with a particular Email address.
     *
     * @param email the user's Email address.
     * @return a User object corresponding to the user with Email. Returns null if the user does
     * not exist in the database.
     */
    User getUserByEmail(String email);

    /**
     * Gets the User object corresponding to the user with a particular email address.
     *
     * @param userId the user's user ID.
     * @return a User object corresponding to the user with email. Returns null if the user does
     * not exist in the database.
     */
    User getUserById(int userId);

    /**
     * Unknown what to be updating currently.
     * Current ideas: changePassword, authenticateUser, changeEmail.
     *
     */
    boolean updateUser(int userId, String firstName, String lastName, double desiredCompensation, HashSet<String> tags);

    /**
     * Deletes the user associated with userId from the database.
     *
     * @param userId the user id of the user to delete.
     */
    boolean deleteUser(int userId);

    /**
     * Adds a set of tags to the database. More specifically, the tags are added to the
     * UserTags table.
     *
     * @param userId the id of the project to add the tags to.
     * @param tags a set of tags associated with the project.
     */
    boolean addTags(int userId, HashSet<String> tags);

    /**
     * Removes a set of tags to the database. More specifically, the tags are removed from the
     * ProjectTags table.
     *
     * @param userId the id of the project to add the tags to.
     * @param tags a set of tags associated with the project.
     */
    boolean removeTags(int userId, HashSet<String> tags);

    /**
     * Returns the hashed password of the user associated with the inputted email.
     *
     * @param email the email of the user whose password to retrieve.
     * @return the hashed password of the user associated with the email.
     */
    String getPasswordByEmail(String email);
}