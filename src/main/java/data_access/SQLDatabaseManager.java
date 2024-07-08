package data_access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class SQLDatabaseManager implements Database {
    private final String DATABASE_NAME;
    private Connection connection;

    /**
     * Constructs a DatabaseManager object.
     *
     * @param databaseName The name of the database to manage. Note that this must include a '.db' file extension.
     */
    public SQLDatabaseManager(String databaseName) {
        this.DATABASE_NAME = databaseName;
    }

    /**
     * Attempts to establish a connection to the database.
     */
    @Override
    public void connect() {
        try {
            String path = "jdbc:sqlite:" + this.DATABASE_NAME;
            this.connection = DriverManager.getConnection(path);
            System.out.println("Connected to the database.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Attempts to disconnect the connection from the database.
     */
    @Override
    public void disconnect() {
        if (this.connection != null) {
            try {
                this.connection.close();
                System.out.println("Disconnected from the database.");
            } catch (SQLException e) {
                System.err.println("Error while disconnecting: " + e.getMessage());
            } finally {
                this.connection = null;
            }
        }
    }

    protected Connection getConnection() {
        return this.connection;
    }

    /**
     * Initializes the database with the required tables if they do not already exist.
     */
    @Override
    public abstract void initialize();
}