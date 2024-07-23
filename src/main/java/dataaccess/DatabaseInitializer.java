package dataaccess;

/**
 * Utility class for initializing the database.
 * This class should not be instantiated.
 */
public class DatabaseInitializer {

    // Private constructor to prevent instantiation
    private DatabaseInitializer() {}

    /**
     * Initializes the database by connecting to it and creating necessary tables.
     * This method retrieves the repository instances from DataAccessConfig and
     * performs the connection and initialization steps.
     */
    public static void initializeDatabase() {
        Database init_upr = (Database) DataAccessConfig.getUserProjectsRepository();
        Database init_ur = (Database) DataAccessConfig.getUserRepository();
        Database init_pr = (Database) DataAccessConfig.getProjectRepository();
        Database init_ar = (Database) DataAccessConfig.getApplicationRepository();

        init_upr.connect();
        init_ur.connect();
        init_pr.connect();
        init_ar.connect();

        init_upr.initialize();
        init_ur.initialize();
        init_pr.initialize();
        init_ar.initialize();
    }
}
