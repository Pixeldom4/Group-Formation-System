package dataaccess;

import dataaccess.database.ApplicationRepository;
import dataaccess.database.ProjectRepository;
import dataaccess.database.UserProjectsRepository;
import dataaccess.database.UserRepository;
import dataaccess.inmemory.LoginUserDetails;
import dataaccess.local.*;
import dataaccess.database.manager.*;

/**
 * Configuration class for setting up data access repositories.
 * Provides methods to get the appropriate repositories based on configuration.
 */
public class DataAccessConfig {
    public static int USE_LOCAL = 0; // Set this to 1 to use local, 0 to use database

    private static final String databaseName = "projectDatabase.db";

    // Database Managers
    private static final ApplicationManager applicationManager = new ApplicationManager(databaseName);
    private static final ProjectManager projectManager = new ProjectManager(databaseName);
    private static final ProjectTagsManager projectTagsManager = new ProjectTagsManager(databaseName);
    private static final ProjectEmbeddingsManager projectEmbeddingsManager = new ProjectEmbeddingsManager(databaseName);
    private static final UserProjectsManager userProjectsManager = new UserProjectsManager(databaseName);
    private static final UserManager userManager = new UserManager(databaseName);
    private static final UserTagsManager userTagsManager = new UserTagsManager(databaseName);

    // Database Repositories
    private static final IUserProjectsRepository userProjectsRepository = new UserProjectsRepository(userProjectsManager);
    private static final IUserRepository userRepository = new UserRepository(userManager, userTagsManager, userProjectsManager);
    private static final IProjectRepository projectRepository = new ProjectRepository(projectManager, projectTagsManager, projectEmbeddingsManager, userProjectsManager);
    private static final IApplicationRepository applicationRepository = new ApplicationRepository(applicationManager);

    // Local Repositories
    private final static ILocalEmbedRepository embedDataAccess = new LocalEmbedRepository();
    private final static IProjectRepository projectDataAccess = new LocalProjectRepository();
    private final static IUserRepository userDataAccess = new LocalUserRepository();
    private final static IUserProjectsRepository userProjectsDataAccess = new LocalUserProjectsRepository();
    private final static IApplicationRepository applicationDataAccess = new LocalApplicationRepository();

    // Login Details
    private final static ILoginUserDetails loginUserDetails = new LoginUserDetails();

    // Static block to initialize the database when the class is loaded
    static {
        DatabaseInitializer.initializeDatabase();
    }

    // This class should not be instantiated
    private DataAccessConfig() { }

    public static String getImplementation() {
        if (USE_LOCAL == 1) {
            return "Using local implementation";
        } else {
            return "Using database implementation";
        }
    }


    /**
     * Returns the ProjectRepository that will be used in the application.
     *
     * @return the ProjectRepository instance
     */
    public static IProjectRepository getProjectRepository() {
        return USE_LOCAL == 1 ? projectDataAccess : projectRepository;
    }

    /**
     * Returns the LocalEmbedRepository that will be used in the application.
     *
     * @return the LocalEmbedRepository instance
     */
    public static ILocalEmbedRepository getEmbedDataAccess() {
        return embedDataAccess;
    }

    /**
     * Returns the UserRepository that will be used in the application.
     *
     * @return the UserRepository instance
     */
    public static IUserRepository getUserRepository() {
        return USE_LOCAL == 1 ? userDataAccess : userRepository;
    }

    /**
     * Returns the UserProjectsRepository that will be used in the application.
     *
     * @return the UserProjectsRepository instance
     */
    public static IUserProjectsRepository getUserProjectsRepository() {
        return USE_LOCAL == 1 ? userProjectsDataAccess : userProjectsRepository;
    }

    /**
     * Returns the ApplicationRepository that will be used in the application.
     *
     * @return the ApplicationRepository instance
     */
    public static IApplicationRepository getApplicationRepository() {
        return USE_LOCAL == 1 ? applicationDataAccess : applicationRepository;
    }

    /**
     * Returns the LoginUserDetails that will be used in the application.
     *
     * @return the LoginUserDetails instance
     */
    public static ILoginUserDetails getLoginUserDetails() {
        return loginUserDetails;
    }

    /**
     * Returns the path to the CSV files for the application.
     *
     * @return the path to the CSV files
     */
    public static String getProjectCSVPath() {
        return "local_data/projects/";
    }

    /**
     * Returns the UserManager instance that will be used in the application.
     *
     * @return the UserManager instance
     */
    public static UserManager getUserManager() {
        return userManager;
    }

    /**
     * Returns the UserTagsManager instance that will be used in the application.
     *
     * @return the UserTagsManager instance
     */
    public static UserTagsManager getUserTagsManager() {
        return userTagsManager;
    }

    /**
     * Returns the ProjectManager instance that will be used in the application.
     *
     * @return the ProjectManager instance
     */
    public static ProjectManager getProjectManager() {
        return projectManager;
    }

    /**
     * Returns the ProjectTagsManager instance that will be used in the application.
     *
     * @return the ProjectTagsManager instance
     */
    public static ProjectTagsManager getProjectTagsManager() {
        return projectTagsManager;
    }

    /**
     * Returns the ProjectEmbeddingsManager instance that will be used in the application.
     *
     * @return the ProjectEmbeddingsManager instance
     */
    public static ProjectEmbeddingsManager getProjectEmbeddingsManager() {
        return projectEmbeddingsManager;
    }

    /**
     * Returns the UserProjectsManager instance that will be used in the application.
     *
     * @return the UserProjectsManager instance
     */
    public static UserProjectsManager getUserProjectsManager() {
        return userProjectsManager;
    }

    /**
     * Returns the ApplicationManager instance that will be used in the application.
     *
     * @return the ApplicationManager instance
     */
    public static ApplicationManager getApplicationManager() {
        return applicationManager;
    }
}
