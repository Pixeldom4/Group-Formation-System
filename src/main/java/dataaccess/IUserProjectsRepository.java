package dataaccess;

import java.util.HashSet;

/**
 * Interface for user projects repository operations.
 * Defines methods for managing user project relationships in the database.
 */
public interface IUserProjectsRepository {
    /**
     * Adds a User-Project association.
     * @param userId The ID of the user.
     * @param projectId The ID of the project.
     */
    boolean addUserToProject(int userId, int projectId);

    /**
     * Removes a User-Project association.
     *
     * @param userId The ID of the user.
     * @param projectId The ID of the project.
     */
     boolean removeUserFromProject(int userId, int projectId);

    /**
     * Removes all project associations for a given user from the UserProjects table.
     * This method deletes all records where the specified user ID is found.
     *
     * @param userId The ID of the user whose project associations are to be removed.
     */
     boolean removeUserFromAllProjects(int userId);

    /**
     * Removes all user associations for a given project from the UserProjects table.
     * This method deletes all records where the specified project ID is found.
     *
     * @param projectId The ID of the project whose user associations are to be removed.
     */
     boolean removeProjectFromAllUsers(int projectId);

    /**
     * Retrieves all projects Ids for a specific user.
     *
     * @param userId The ID of the user.
     * @return A set of project IDs associated with the user.
     */
     HashSet<Integer> getProjectIdsForUser(int userId);

    /**
     * Retrieves all user Ids for a specific project.
     *
     * @param projectId The ID of the project.
     * @return A set of user IDs associated with the project.
     */
     HashSet<Integer> getUserIdsForProject(int projectId);
}
