package dataaccess.database.manager;

import dataaccess.Database;
import dataaccess.database.manager.DatabaseConnection;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

/**
 * An abstract class that manages the SQL database connection and initialization.
 */
public abstract class SQLDatabaseManager implements Database {
    private final String DATABASE_NAME;

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
        DatabaseConnection.getInstance(this.DATABASE_NAME); // Ensure connection is established
    }

    /**
     * Attempts to disconnect the connection from the database.
     */
    @Override
    public void disconnect() {
        DatabaseConnection.getInstance(this.DATABASE_NAME).disconnect();
    }

    /**
     * Getter method for the database connection. If there is no current connection,
     * it connects to the database and returns the connection.
     *
     * @return the connection for this database.
     */
    protected Connection getConnection() {
        return DatabaseConnection.getInstance(this.DATABASE_NAME).getConnection();
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
