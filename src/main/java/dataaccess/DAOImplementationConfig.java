package dataaccess;

import dataaccess.local.EmbedDataAccessInterface;
import dataaccess.local.LocalEmbedDataAccessObject;
import dataaccess.local.LocalProjectDataAccessObject;

public class DAOImplementationConfig {

    private final static EmbedDataAccessInterface embedDataAccess = new LocalEmbedDataAccessObject();
    private final static IProjectRepository projectDataAccess = new LocalProjectDataAccessObject();

    public static IProjectRepository getProjectDataAccess() {
        return projectDataAccess;
    }

    public static EmbedDataAccessInterface getEmbedDataAccess() {
        return embedDataAccess;
    }

    public static String getProjectCSVPath() {
        return "local_data/projects/";
    }

}
