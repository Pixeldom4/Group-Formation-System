package dataaccess.database.manager;

import dataaccess.database.SQLDatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

/**
 * Manages project tags-related operations in the database.
 */
public class ProjectTagsManager extends SQLDatabaseManager {

    /**
     * Constructs a ProjectTagsManager with the specified database name.
     *
     * @param databaseName the name of the database.
     */
    public ProjectTagsManager(String databaseName) {
        super(databaseName);
    }

    @Override
    public void initialize() {
        String projectTagsSql = "CREATE TABLE IF NOT EXISTS ProjectTags (ProjectId INTEGER NOT NULL, Tag TEXT NOT NULL, PRIMARY KEY(ProjectId, Tag), FOREIGN KEY(ProjectId) REFERENCES Projects(Id))";
        super.initializeTables(projectTagsSql);
    }

    /**
     * Adds tags to a project in the database.
     *
     * @param projectId the ID of the project.
     * @param tags      the tags to add.
     * @return true if the tags were added successfully, false otherwise.
     */
    public boolean addTags(int projectId, HashSet<String> tags) {
        String sql = "INSERT INTO ProjectTags (ProjectId, Tag) VALUES (?, ?)";
        Connection connection = getConnection();
        return executeTagUpdates(connection, projectId, tags, sql);
    }

    /**
     * Removes tags from a project in the database.
     *
     * @param projectId the ID of the project.
     * @param tags      the tags to remove.
     * @return true if the tags were removed successfully, false otherwise.
     */
    public boolean removeTags(int projectId, HashSet<String> tags) {
        String sql = "DELETE FROM ProjectTags WHERE ProjectId = ? AND Tag = ?";
        Connection connection = getConnection();
        return executeTagUpdates(connection, projectId, tags, sql);
    }

    /**
     * Retrieves tags for a project from the database.
     *
     * @param projectId the ID of the project.
     * @return a HashSet of tags for the specified project.
     */
    public HashSet<String> getTagsForProject(int projectId) {
        String sql = "SELECT Tag FROM ProjectTags WHERE ProjectId = ?";
        HashSet<String> tags = new HashSet<>();
        Connection connection = getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, projectId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    tags.add(rs.getString("Tag"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return tags;
    }

    /**
     * Helper method to execute tag updates (add/remove) in the database.
     *
     * @param connection the database connection.
     * @param projectId  the ID of the project.
     * @param tags       the tags to add/remove.
     * @param sql        the SQL query to execute.
     * @return true if the operation was successful, false otherwise.
     */
    private boolean executeTagUpdates(Connection connection, int projectId, HashSet<String> tags, String sql) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (String tag : tags) {
                preparedStatement.setInt(1, projectId);
                preparedStatement.setString(2, tag);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }
}
