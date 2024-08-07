package dataaccess;

import config.DataAccessConfig;

/**
 * Utility class for initializing the database.
 * <p>
 * Provides a method to initialize the database by connecting to it and creating necessary tables.
 */
public class DatabaseInitializer {

    // Private constructor to prevent instantiation
    private DatabaseInitializer() {}

    /**
     * Initializes the database by connecting to it and creating necessary tables.
     */
    public static void initializeDatabase() {
        initializeManagers();
    }

    /**
     * Connects to and initializes all the database managers.
     */
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
