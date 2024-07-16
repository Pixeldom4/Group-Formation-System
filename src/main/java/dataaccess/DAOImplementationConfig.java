package dataaccess;

import dataaccess.database.ProjectRepository;
import dataaccess.database.UserProjectsRepository;
import dataaccess.database.UserRepository;
import dataaccess.local.*;

public class DAOImplementationConfig {

    private static final String databaseName = "database.db";
    private static final IUserProjectsRepository userProjectsRepository = new UserProjectsRepository(databaseName);
    private static final IUserRepository userRepository = new UserRepository(databaseName, userProjectsRepository);
    private static final IProjectRepository projectRepository = new ProjectRepository(databaseName, userProjectsRepository);

    private final static EmbedDataAccessInterface embedDataAccess = new LocalEmbedDataAccessObject();
    private final static IProjectRepository projectDataAccess = new LocalProjectDataAccessObject();
    private final static IUserRepository userDataAccess = new LocalUserRepository();
    private final static IUserProjectsRepository userProjectsDataAccess = new LocalUserProjectsRepository();

    // This class should not be instantiated
    private DAOImplementationConfig() { }

    /**
     * Initializes the database
     */
    public static void initializeDatabase(){
        // Casting so that database can be initialized with parent class methods
        UserProjectsRepository init_upr = (UserProjectsRepository) userProjectsRepository;
        UserRepository init_ur = (UserRepository) userRepository;
        ProjectRepository init_pr = (ProjectRepository) projectRepository;

        init_upr.connect();
        init_ur.connect();
        init_pr.connect();

        init_upr.initialize();
        init_ur.initialize();
        init_pr.initialize();
    }

    /**
     * Returns the ProjectRepository that would be used in the application
     * @return the ProjectRepository
     */
    public static IProjectRepository getProjectRepository() {
        return projectDataAccess;
    }

    public static EmbedDataAccessInterface getEmbedDataAccess() {
        return embedDataAccess;
    }

    /**
     * Returns the UserRepository that would be used in the application
     * @return the UserRepository
     */
    public static IUserRepository getUserRepository() {
        return userDataAccess;
    }

    /**
     * Returns the UserProjectsRepository that would be used in the application
     * @return the UserProjectsRepository
     */
    public static IUserProjectsRepository getUserProjectsRepository() {
        return userProjectsDataAccess;
    }

    /**
     * Returns the path to the CSV files for the application
     * @return the path to the CSV files
     */
    public static String getProjectCSVPath() {
        return "local_data/projects/";
    }

}
