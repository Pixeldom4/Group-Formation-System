package dataaccess.database;

import dataaccess.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * An abstract class that manages the SQL database connection and initialization.
 */
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
            initialize();
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

    /**
     * Getter method for the database connection. If there is no current connection,
     * it connects to the database and returns the connection.
     *
     * @return the connection for this database.
     */
    protected Connection getConnection() {
        if (this.connection == null) {
            this.connect();
        }
        return this.connection;
    }

    /**
     * Initializes the database with the required tables if they do not already exist.
     */
    @Override
    public abstract void initialize();

    /**
     * Initializes the database with the provided SQL statements.
     * <p>
     * This method executes each SQL statement in the provided array of SQL statements
     * to set up the necessary database tables. If any error occurs during the execution,
     * the transaction is rolled back to maintain database integrity.
     * </p>
     *
     * @param sqlStatements An array of SQL statements to execute for initializing the database tables.
     */
    protected void initializeTables(String... sqlStatements) {
        Connection connection = getConnection();
        try {
            connection.setAutoCommit(false);

            try (Statement statement = connection.createStatement()) {
                for (String sql : sqlStatements) {
                    statement.executeUpdate(sql);
                }
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                System.err.println(rollbackException.getMessage());
            }
            System.err.println(e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}