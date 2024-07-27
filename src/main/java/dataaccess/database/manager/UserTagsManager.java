package dataaccess.database.manager;

import dataaccess.database.SQLDatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class UserTagsManager extends SQLDatabaseManager {

    public UserTagsManager(String databaseName) {
        super(databaseName);
    }

    /**
     * Initializes the database with the required tables if they do not already exist.
     */
    @Override
    public void initialize() {
        String userTagsSql = "CREATE TABLE IF NOT EXISTS UserTags (UserId INTEGER NOT NULL, Tag TEXT NOT NULL, PRIMARY KEY(UserId, Tag), FOREIGN KEY(UserId) REFERENCES Users(Id))";
        super.initializeTables(userTagsSql);
    }

    public boolean addTags(int userId, HashSet<String> tags) {
        String sql = "INSERT INTO UserTags (UserId, Tag) VALUES (?, ?)";
        return executeTagUpdates(userId, tags, sql);
    }

    public boolean removeTags(int userId, HashSet<String> tags) {
        String sql = "DELETE FROM UserTags WHERE UserId = ? AND Tag = ?";
        return executeTagUpdates(userId, tags, sql);
    }

    public HashSet<String> getTagsForUser(int userId) {
        String sql = "SELECT Tag FROM UserTags WHERE UserId = ?";
        HashSet<String> tags = new HashSet<>();

        Connection connection = super.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
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

    private boolean executeTagUpdates(int userId, HashSet<String> tags, String sql) {
        Connection connection = super.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (String tag : tags) {
                preparedStatement.setInt(1, userId);
                preparedStatement.setString(2, tag);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            return true;
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }
}
