package dataaccess.database.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

/**
 * Manages user tags-related operations in the database.
 */
public class UserTagsManager extends SQLDatabaseManager {

    /**
     * Constructs a UserTagsManager with the specified database name.
     *
     * @param databaseName the name of the database.
     */
    public UserTagsManager(String databaseName) {
        super(databaseName);
    }

    /**
     * Initializes the UserTags table in the database.
     */
    @Override
    public void initialize() {
        String userTagsSql = "CREATE TABLE IF NOT EXISTS UserTags (UserId INTEGER NOT NULL, Tag TEXT NOT NULL, PRIMARY KEY(UserId, Tag), FOREIGN KEY(UserId) REFERENCES Users(Id))";
        super.initializeTables(userTagsSql);
    }

    /**
     * Adds tags to a user in the database.
     *
     * @param userId the ID of the user.
     * @param tags   the tags to add.
     * @return true if the tags were added successfully, false otherwise.
     */
    public boolean addTags(int userId, HashSet<String> tags) {
        String sql = "INSERT INTO UserTags (UserId, Tag) VALUES (?, ?)";
        return executeTagUpdates(userId, tags, sql);
    }

    /**
     * Removes tags from a user in the database.
     *
     * @param userId the ID of the user.
     * @param tags   the tags to remove.
     * @return true if the tags were removed successfully, false otherwise.
     */
    public boolean removeTags(int userId, HashSet<String> tags) {
        String sql = "DELETE FROM UserTags WHERE UserId = ? AND Tag = ?";
        return executeTagUpdates(userId, tags, sql);
    }

    /**
     * Retrieves tags for a user from the database.
     *
     * @param userId the ID of the user.
     * @return a HashSet of tags for the specified user.
     */
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

    /**
     * Helper method to execute tag updates (add/remove) in the database.
     *
     * @param userId the ID of the user.
     * @param tags   the tags to add/remove.
     * @param sql    the SQL query to execute.
     * @return true if the operation was successful, false otherwise.
     */
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
