package dataaccess.database.manager;

import dataaccess.database.SQLDatabaseManager;
import dataaccess.database.UserProjectsRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class UserProjectsManager extends SQLDatabaseManager {

    public UserProjectsManager(String databaseManager) {
        super(databaseManager);
    }

    /**
     * Initializes the database with the required tables if they do not already exist.
     */
    @Override
    public void initialize() {
        String sql = "CREATE TABLE IF NOT EXISTS UserProjects (UserId INTEGER NOT NULL, ProjectId INTEGER NOT NULL, PRIMARY KEY(UserId, ProjectId), FOREIGN KEY(UserId) REFERENCES Users(Id), FOREIGN KEY(ProjectId) REFERENCES Projects(Id));";
        super.initializeTables(sql);
    }

    /**
     * Adds a User-Project association.
     *
     * @param userId The ID of the user.
     * @param projectId The ID of the project.
     */
    public boolean addUserToProject(int userId, int projectId) {
        String sql = "INSERT INTO UserProjects (UserId, ProjectId) VALUES (?, ?)";
        return executeUpdate(userId, projectId, sql);
    }

    /**
     * Removes a User-Project association.
     *
     * @param userId The ID of the user.
     * @param projectId The ID of the project.
     */
    public boolean removeUserFromProject(int userId, int projectId) {
        String sql = "DELETE FROM UserProjects WHERE UserId = ? AND ProjectId = ?";
        return executeUpdate(userId, projectId, sql);
    }

    /**
     * Removes all project associations for a given user from the UserProjects table.
     * This method deletes all records where the specified user ID is found.
     *
     * @param userId The ID of the user whose project associations are to be removed.
     */
    public boolean removeUserFromAllProjects(int userId) {
        String sql = "DELETE FROM UserProjects WHERE UserId = ?";
        Connection connection = super.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }

    /**
     * Removes all user associations for a given project from the UserProjects table.
     * This method deletes all records where the specified project ID is found.
     *
     * @param projectId The ID of the project whose user associations are to be removed.
     */
    public boolean removeProjectFromAllUsers(int projectId) {
        String sql = "DELETE FROM UserProjects WHERE ProjectId = ?";
        Connection connection = super.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, projectId);
            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }

    /**
     * Retrieves all project Ids for a specific user.
     *
     * @param userId The ID of the user.
     * @return A set of project IDs associated with the user.
     */
    public HashSet<Integer> getProjectIdsForUser(int userId) {
        String sql = "SELECT ProjectId FROM UserProjects WHERE UserId = ?";
        HashSet<Integer> projectIds = new HashSet<>();
        Connection connection = super.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    projectIds.add(rs.getInt("ProjectId"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return projectIds;
    }

    /**
     * Retrieves all user Ids for a specific project.
     *
     * @param projectId The ID of the project.
     * @return A set of user IDs associated with the project.
     */
    public HashSet<Integer> getUserIdsForProject(int projectId) {
        String sql = "SELECT UserId FROM UserProjects WHERE ProjectId = ?";
        HashSet<Integer> userIds = new HashSet<>();
        Connection connection = super.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, projectId);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    userIds.add(rs.getInt("UserId"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return userIds;
    }

    /** Executes an update query for the UserProjects table.
     *
     * @param userId the ID of the user.
     * @param projectId the ID of the project.
     * @param sql the SQL statement to execute.
     * @return true if the update was successful, false otherwise.
     */
    private boolean executeUpdate(int userId, int projectId, String sql) {
        Connection connection = super.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, projectId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }
}
