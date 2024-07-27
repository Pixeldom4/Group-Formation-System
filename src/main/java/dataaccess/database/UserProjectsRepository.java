package dataaccess.database;

import dataaccess.IUserProjectsRepository;
import dataaccess.database.manager.UserProjectsManager;

import java.util.HashSet;

/**
 * Facade class that implements the IUserProjectsRepository interface and provides methods for managing user-project relationships in the database.
 */
public class UserProjectsRepository implements IUserProjectsRepository {

    private final UserProjectsManager userProjectsManager;

    /**
     * Constructs a UserProjectsRepository with the specified UserProjectsManager.
     *
     * @param userProjectsManager the UserProjectsManager instance.
     */
    public UserProjectsRepository(UserProjectsManager userProjectsManager) {
        this.userProjectsManager = userProjectsManager;
    }


    /**
     * Adds a user to a project.
     *
     * @param userId the ID of the user.
     * @param projectId the ID of the project.
     * @return true if the addition was successful, false otherwise.
     */
    @Override
    public boolean addUserToProject(int userId, int projectId) {
        return userProjectsManager.addUserToProject(userId, projectId);
    }

    /**
     * Removes a user from a project.
     *
     * @param userId the ID of the user.
     * @param projectId the ID of the project.
     * @return true if the removal was successful, false otherwise.
     */
    @Override
    public boolean removeUserFromProject(int userId, int projectId) {
        return userProjectsManager.removeUserFromProject(userId, projectId);
    }

    /**
     * Removes a user from all projects.
     *
     * @param userId the ID of the user.
     * @return true if the removal was successful, false otherwise.
     */
    @Override
    public boolean removeUserFromAllProjects(int userId) {
        return userProjectsManager.removeUserFromAllProjects(userId);
    }

    /**
     * Removes a project from all users.
     *
     * @param projectId the ID of the project.
     * @return true if the removal was successful, false otherwise.
     */
    @Override
    public boolean removeProjectFromAllUsers(int projectId) {
        return userProjectsManager.removeProjectFromAllUsers(projectId);
    }

    /**
     * Retrieves project IDs for a specific user.
     *
     * @param userId the ID of the user.
     * @return a HashSet of project IDs.
     */
    @Override
    public HashSet<Integer> getProjectIdsForUser(int userId) {
        return userProjectsManager.getProjectIdsForUser(userId);
    }

    /**
     * Retrieves user IDs for a specific project.
     *
     * @param projectId the ID of the project.
     * @return a HashSet of user IDs.
     */
    @Override
    public HashSet<Integer> getUserIdsForProject(int projectId) {
        return userProjectsManager.getUserIdsForProject(projectId);
    }
}
