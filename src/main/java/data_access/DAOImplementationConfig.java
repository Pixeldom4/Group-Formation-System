package data_access;

public class DAOImplementationConfig {

    private final static EmbedDataAccessInterface embedDataAccess = new LocalEmbedDataAccessObject();
    private final static ProjectDataAccessInterface projectDataAccess = new LocalProjectDataAccessObject();

    public static ProjectDataAccessInterface getProjectDataAccess() {
        return projectDataAccess;
    }

    public static EmbedDataAccessInterface getEmbedDataAccess() {

        return embedDataAccess;
    }

    public static String getProjectCSVPath() {
        return "local_data/projects/";
    }

    public static ProjectSearchInterface newProjectSearchObject() {
        return new LocalProjectSearchObject();
    }
}
