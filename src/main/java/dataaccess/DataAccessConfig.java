package dataaccess;

import dataaccess.database.ApplicationRepository;
import dataaccess.database.ProjectRepository;
import dataaccess.database.UserProjectsRepository;
import dataaccess.database.UserRepository;
import dataaccess.inmemory.LoginUserDetails;
import dataaccess.local.*;

/**
 * Configuration class for setting up data access repositories.
 * Provides methods to get the appropriate repositories based on configuration.
 */
public class DataAccessConfig {
    public static int USE_LOCAL = 0; // Set this to 1 to use local, 0 to use database

    private static final String databaseName = "database.db";
    private static final IUserProjectsRepository userProjectsRepository = new UserProjectsRepository(databaseName);
    private static final IUserRepository userRepository = new UserRepository(databaseName, userProjectsRepository);
    private static final IProjectRepository projectRepository = new ProjectRepository(databaseName, userProjectsRepository);
    private static final IApplicationRepository applicationRepository = new ApplicationRepository(databaseName);

    private final static ILocalEmbedRepository embedDataAccess = new LocalEmbedRepository();
    private final static IProjectRepository projectDataAccess = new LocalProjectRepository();
    private final static IUserRepository userDataAccess = new LocalUserRepository();
    private final static IUserProjectsRepository userProjectsDataAccess = new LocalUserProjectsRepository();
    private final static IApplicationRepository applicationDataAccess = new LocalApplicationRepository();

    private final static ILoginUserDetails loginUserDetails = new LoginUserDetails();

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
     * Initializes the database by connecting to it and creating necessary tables.
     */
    public static void initializeDatabase(){
        Database init_upr = (Database) userProjectsRepository;
        Database init_ur = (Database) userRepository;
        Database init_pr = (Database) projectRepository;
        Database init_ar = (Database) applicationRepository;

        init_upr.connect();
        init_ur.connect();
        init_pr.connect();
        init_ar.connect();

        init_upr.initialize();
        init_ur.initialize();
        init_pr.initialize();
        init_ar.initialize();
    }

    /**
     * Returns the ProjectRepository that will be used in the application.
     * @return the ProjectRepository instance
     */
    public static IProjectRepository getProjectRepository() {
        return USE_LOCAL == 1 ? projectDataAccess : projectRepository;
    }

    /**
     * Returns the LocalEmbedRepository that will be used in the application.
     * @return the LocalEmbedRepository instance
     */
    public static ILocalEmbedRepository getEmbedDataAccess() {
        return embedDataAccess;
    }

    /**
     * Returns the UserRepository that will be used in the application.
     * @return the UserRepository instance
     */
    public static IUserRepository getUserRepository() {
        return USE_LOCAL == 1 ? userDataAccess : userRepository;
    }

    /**
     * Returns the UserProjectsRepository that will be used in the application.
     * @return the UserProjectsRepository instance
     */
    public static IUserProjectsRepository getUserProjectsRepository() {
        return USE_LOCAL == 1 ? userProjectsDataAccess : userProjectsRepository;
    }

    /**
     * Returns the ApplicationRepository that will be used in the application.
     * @return the ApplicationRepository instance
     */
    public static IApplicationRepository getApplicationRepository() {
        return USE_LOCAL == 1 ? applicationDataAccess : applicationRepository;
    }

    /**
     * Returns the LoginUserDetails that will be used in the application.
     * @return the LoginUserDetails instance
     */
    public static ILoginUserDetails getLoginUserDetails() {
        return loginUserDetails;
    }

    /**
     * Returns the path to the CSV files for the application.
     * @return the path to the CSV files
     */
    public static String getProjectCSVPath() {
        return "local_data/projects/";
    }
}
