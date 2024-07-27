package dataaccess.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Helper class for database operations.
 */
public class DatabaseHelper {

    private String databaseName;

    public DatabaseHelper(String databaseName) {
        this.databaseName = databaseName;
    }

    /**
     * Wipes the database by dropping all tables except for sqlite_sequence and resetting auto-increment counters.
     */
    public void wipeDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);
             Statement statement = connection.createStatement()) {

            // Disable foreign key constraints to allow dropping tables
            statement.executeUpdate("PRAGMA foreign_keys = OFF");

            // Get all table names except sqlite_sequence
            ResultSet rs = statement.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name != 'sqlite_sequence'");
            while (rs.next()) {
                String tableName = rs.getString("name");
                statement.executeUpdate("DROP TABLE IF EXISTS " + tableName);
            }

            // Re-enable foreign key constraints
            statement.executeUpdate("PRAGMA foreign_keys = ON");

            // Reset auto-increment counters
            statement.executeUpdate("DELETE FROM sqlite_sequence");
        } catch (SQLException e) {
            System.err.println("Error wiping database: " + e.getMessage());
        }
    }
}
