package dataaccess.database.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton class for managing database connections.
 * This class ensures that only one instance of the database connection is created.
 * The connection is shared among multiple clients, with a reference counting mechanism
 * to close the connection when it is no longer needed.
 */
public class DatabaseConnection {
    private static volatile DatabaseConnection instance;
    private Connection connection;
    private int referenceCount = 0;

    /**
     * Private constructor to initialize the database connection.
     *
     * @param databaseName The name of the database to connect to.
     */
    private DatabaseConnection(String databaseName) {
        try {
            String path = "jdbc:sqlite:" + databaseName;
            this.connection = DriverManager.getConnection(path);
            System.out.println("Connected to the database.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Returns the singleton instance of the DatabaseConnection class.
     * If an instance does not already exist, it is created.
     *
     * @param databaseName The name of the database to connect to.
     * @return The singleton instance of DatabaseConnection.
     */
    public static DatabaseConnection getInstance(String databaseName) {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection(databaseName);
                }
            }
        }
        instance.referenceCount++;
        return instance;
    }

    /**
     * Returns the database connection.
     *
     * @return The active database connection.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Releases the database connection.
     * Decreases the reference count, and if no more references exist,
     * closes the connection.
     */
    public void releaseConnection() {
        if (referenceCount > 0) {
            referenceCount--;
        }
        if (referenceCount == 0 && this.connection != null) {
            try {
                this.connection.close();
                System.out.println("Disconnected from the database.");
            } catch (SQLException e) {
                System.err.println("Error while disconnecting: " + e.getMessage());
            } finally {
                this.connection = null;
                instance = null;
            }
        }
    }

    /**
     * Forces the closure of the database connection.
     * This method ensures that the connection is closed even if it is still in use.
     */
    private void forceClose() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error while force closing: " + e.getMessage());
        } finally {
            connection = null;
            instance = null;
        }
    }

    /**
     * Disconnects the database connection, releasing any held resources.
     * This method calls releaseConnection and ensures that all connections are closed.
     */
    public static void disconnect() {
        if (instance != null) {
            instance.releaseConnection();
            instance.forceClose(); // Ensure all connections are closed
        }
    }
}
