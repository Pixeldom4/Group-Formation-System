package data_access;

/**
 * The Database interface provides methods for establishing and managing connections
 * to a database.
 */
public interface Database {

    /** Attempts to establish a connection to the database. */
    void connect();

    /** Attempts to disconnect the connection from the database. */
    void disconnect();

    /** Initializes the database with the required tables if they do not already exist.*/
    void initialize();
}