package dataaccess.database.facade;

import dataaccess.IUserRepository;
import dataaccess.database.manager.UserManager;
import dataaccess.database.manager.UserTagsManager;
import dataaccess.database.manager.UserProjectsManager;
import entities.User;

import java.util.HashSet;

public class UserFacade implements IUserRepository {

    private final UserManager userManager;
    private final UserTagsManager userTagsManager;
    private final UserProjectsManager userProjectsManager;

    public UserFacade(UserManager userManager, UserTagsManager userTagsManager, UserProjectsManager userProjectsManager) {
        this.userManager = userManager;
        this.userTagsManager = userTagsManager;
        this.userProjectsManager = userProjectsManager;
    }

    @Override
    public User createUser(String email, String firstName, String lastName, HashSet<String> tags, double desiredCompensation, String password) {
        User user = userManager.createUser(email, firstName, lastName, desiredCompensation, password);
        if (user != null) {
            userTagsManager.addTags(user.getUserId(), tags);
            user.setTags(tags);
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userManager.getUserByEmail(email);
        if (user != null) {
            HashSet<String> tags = userTagsManager.getTagsForUser(user.getUserId());
            user.setTags(tags);
        }
        return user;
    }

    @Override
    public User getUserById(int userId) {
        User user = userManager.getUserById(userId);
        if (user != null) {
            HashSet<String> tags = userTagsManager.getTagsForUser(userId);
            user.setTags(tags);
        }
        return user;
    }

    @Override
    public boolean updateUser(int userId, String firstName, String lastName, double desiredCompensation, HashSet<String> tags) {
        boolean isUpdated = userManager.updateUser(userId, firstName, lastName, desiredCompensation);
        if (isUpdated) {
            userTagsManager.removeTags(userId, userTagsManager.getTagsForUser(userId));
            userTagsManager.addTags(userId, tags);
        }
        return isUpdated;
    }

    @Override
    public boolean deleteUser(int userId) {
        userProjectsManager.removeUserFromAllProjects(userId);
        userTagsManager.removeTags(userId, userTagsManager.getTagsForUser(userId));
        return userManager.deleteUser(userId);
    }

    @Override
    public boolean addTags(int userId, HashSet<String> tags) {
        return userTagsManager.addTags(userId, tags);
    }

    @Override
    public boolean removeTags(int userId, HashSet<String> tags) {
        return userTagsManager.removeTags(userId, tags);
    }

    @Override
    public String getPasswordByEmail(String email) {
        return userManager.getPasswordByEmail(email);
    }
}
