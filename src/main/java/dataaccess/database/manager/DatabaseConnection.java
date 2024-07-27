package dataaccess.database.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    private final String DATABASE_NAME;

    private DatabaseConnection(String databaseName) {
        this.DATABASE_NAME = databaseName;
        try {
            String path = "jdbc:sqlite:" + this.DATABASE_NAME;
            this.connection = DriverManager.getConnection(path);
            System.out.println("Connected to the database.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static DatabaseConnection getInstance(String databaseName) {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection(databaseName);
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void disconnect() {
        if (this.connection != null) {
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
}
