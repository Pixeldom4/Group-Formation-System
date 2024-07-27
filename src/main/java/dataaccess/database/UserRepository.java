package dataaccess.database;

import dataaccess.IUserRepository;
import dataaccess.database.manager.UserManager;
import dataaccess.database.manager.UserTagsManager;
import dataaccess.database.manager.UserProjectsManager;
import entities.User;

import java.util.HashSet;

/**
 * Facade class that implements the IUserRepository interface and provides methods for managing users in the database.
 */
public class UserRepository implements IUserRepository {

    private final UserManager userManager;
    private final UserTagsManager userTagsManager;
    private final UserProjectsManager userProjectsManager;

    /**
     * Constructs a UserRepository with the specified UserManager, UserTagsManager, and UserProjectsManager.
     *
     * @param userManager the UserManager instance.
     * @param userTagsManager the UserTagsManager instance.
     * @param userProjectsManager the UserProjectsManager instance.
     */
    public UserRepository(UserManager userManager, UserTagsManager userTagsManager, UserProjectsManager userProjectsManager) {
        this.userManager = userManager;
        this.userTagsManager = userTagsManager;
        this.userProjectsManager = userProjectsManager;
    }


    /**
     * Creates a new user in the database.
     *
     * @param email              the user's email address.
     * @param firstName          the user's first name.
     * @param lastName           the user's last name.
     * @param tags               the user's tags.
     * @param desiredCompensation the user's desired compensation.
     * @param password           the user's password.
     * @return the created User object, or null if the operation fails.
     */
    @Override
    public User createUser(String email, String firstName, String lastName, HashSet<String> tags, double desiredCompensation, String password) {
        User user = userManager.createUser(email, firstName, lastName, desiredCompensation, password);
        if (user != null) {
            userTagsManager.addTags(user.getUserId(), tags);
            user.setTags(tags);
        }
        return user;
    }

    /**
     * Retrieves a user from the database by email.
     *
     * @param email the user's email address.
     * @return the User object if found, or null otherwise.
     */
    @Override
    public User getUserByEmail(String email) {
        User user = userManager.getUserByEmail(email);
        if (user != null) {
            HashSet<String> tags = userTagsManager.getTagsForUser(user.getUserId());
            user.setTags(tags);
        }
        return user;
    }

    /**
     * Retrieves a user from the database by ID.
     *
     * @param userId the user's ID.
     * @return the User object if found, or null otherwise.
     */
    @Override
    public User getUserById(int userId) {
        User user = userManager.getUserById(userId);
        if (user != null) {
            HashSet<String> tags = userTagsManager.getTagsForUser(userId);
            user.setTags(tags);
        }
        return user;
    }

    /**
     * Updates a user's information in the database.
     *
     * @param userId             the user's ID.
     * @param firstName          the user's first name.
     * @param lastName           the user's last name.
     * @param desiredCompensation the user's desired compensation.
     * @param tags               the user's tags.
     * @return true if the update was successful, false otherwise.
     */
    @Override
    public boolean updateUser(int userId, String firstName, String lastName, double desiredCompensation, HashSet<String> tags) {
        boolean isUpdated = userManager.updateUser(userId, firstName, lastName, desiredCompensation);
        if (isUpdated) {
            userTagsManager.removeTags(userId, userTagsManager.getTagsForUser(userId));
            userTagsManager.addTags(userId, tags);
        }
        return isUpdated;
    }

    /**
     * Deletes a user from the database.
     *
     * @param userId the user's ID.
     * @return true if the deletion was successful, false otherwise.
     */
    @Override
    public boolean deleteUser(int userId) {
        userProjectsManager.removeUserFromAllProjects(userId);
        userTagsManager.removeTags(userId, userTagsManager.getTagsForUser(userId));
        return userManager.deleteUser(userId);
    }

    /**
     * Adds tags to a user.
     *
     * @param userId the user's ID.
     * @param tags   the tags to add.
     * @return true if the tags were added successfully, false otherwise.
     */
    @Override
    public boolean addTags(int userId, HashSet<String> tags) {
        return userTagsManager.addTags(userId, tags);
    }

    /**
     * Removes tags from a user.
     *
     * @param userId the user's ID.
     * @param tags   the tags to remove.
     * @return true if the tags were removed successfully, false otherwise.
     */
    @Override
    public boolean removeTags(int userId, HashSet<String> tags) {
        return userTagsManager.removeTags(userId, tags);
    }

    /**
     * Retrieves the password for a user by email.
     *
     * @param email the user's email address.
     * @return the hashed password of the user.
     */
    @Override
    public String getPasswordByEmail(String email) {
        return userManager.getPasswordByEmail(email);
    }
}
