package data_access;

import data_access.local.EmbedDataAccessInterface;
import data_access.local.LocalEmbedDataAccessObject;
import data_access.local.LocalProjectDataAccessObject;

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
