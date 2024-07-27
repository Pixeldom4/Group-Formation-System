package dataaccess.database.manager;

import dataaccess.database.SQLDatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class ProjectTagsManager extends SQLDatabaseManager {

    public ProjectTagsManager(String databaseName) {
        super(databaseName);
    }

    @Override
    public void initialize() {
        String projectTagsSql = "CREATE TABLE IF NOT EXISTS ProjectTags (ProjectId INTEGER NOT NULL, Tag TEXT NOT NULL, PRIMARY KEY(ProjectId, Tag), FOREIGN KEY(ProjectId) REFERENCES Projects(Id))";
        super.initializeTables(projectTagsSql);
    }

    public boolean addTags(int projectId, HashSet<String> tags) {
        String sql = "INSERT INTO ProjectTags (ProjectId, Tag) VALUES (?, ?)";
        Connection connection = getConnection();
        return executeTagUpdates(connection, projectId, tags, sql);
    }

    public boolean removeTags(int projectId, HashSet<String> tags) {
        String sql = "DELETE FROM ProjectTags WHERE ProjectId = ? AND Tag = ?";
        Connection connection = getConnection();
        return executeTagUpdates(connection, projectId, tags, sql);
    }

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
