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
        initializeManagers();
    }

    private static void initializeManagers() {
        DataAccessConfig.getUserManager().connect();
        DataAccessConfig.getUserTagsManager().connect();
        DataAccessConfig.getProjectManager().connect();
        DataAccessConfig.getProjectTagsManager().connect();
        DataAccessConfig.getProjectEmbeddingsManager().connect();
        DataAccessConfig.getUserProjectsManager().connect();
        DataAccessConfig.getApplicationManager().connect();

        DataAccessConfig.getUserManager().initialize();
        DataAccessConfig.getUserTagsManager().initialize();
        DataAccessConfig.getProjectManager().initialize();
        DataAccessConfig.getProjectTagsManager().initialize();
        DataAccessConfig.getProjectEmbeddingsManager().initialize();
        DataAccessConfig.getUserProjectsManager().initialize();
        DataAccessConfig.getApplicationManager().initialize();
    }
}
