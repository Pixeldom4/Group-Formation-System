package dataaccess.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Helper class for database operations.
 */
public class DatabaseHelper {

    private DatabaseHelper(){}
    /**
     * Deletes the database file.
     */
    public static void deleteDatabaseFile(String databaseName) {
        Path path = Paths.get(databaseName);
        try {
            Files.deleteIfExists(path);
            System.out.println("Database file deleted successfully.");
        } catch (IOException e) {
            System.err.println("Error deleting database file: " + e.getMessage());
        }
    }
}