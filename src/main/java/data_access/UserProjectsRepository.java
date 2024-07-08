package data_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

public class UserProjectsRepository extends SQLDatabaseManager {

    public UserProjectsRepository(String databaseName) {
        super(databaseName);
    }

    /**
     * Initializes the database with the required tables if they do not already exist.
     */
    @Override
    public void initialize() {
        String sql = "CREATE TABLE IF NOT EXISTS UserProjects (UserId INTEGER NOT NULL, ProjectId INTEGER NOT NULL, PRIMARY KEY(UserId, ProjectId), FOREIGN KEY(UserId) REFERENCES Users(Id), FOREIGN KEY(ProjectId) REFERENCES Projects(Id));";
        Connection connection = super.getConnection();

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Adds a User-Project association.
     *
     * @param userId The ID of the user.
     * @param projectId The ID of the project.
     */
    public void addUserToProject(int userId, int projectId) {
        String sql = "INSERT INTO UserProjects (UserId, ProjectId) VALUES (?, ?)";
        executeUpdate(userId, projectId, sql);
    }

    private void executeUpdate(int userId, int projectId, String sql) {
        Connection connection = super.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, projectId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Removes a User-Project association.
     *
     * @param userId The ID of the user.
     * @param projectId The ID of the project.
     */
    public void removeUserFromProject(int userId, int projectId) {
        String sql = "DELETE FROM UserProjects WHERE UserId = ? AND ProjectId = ?";
        executeUpdate(userId, projectId, sql);
    }

    /**
     * Retrieves all projects for a specific user.
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
     * Retrieves all users for a specific project.
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
}
