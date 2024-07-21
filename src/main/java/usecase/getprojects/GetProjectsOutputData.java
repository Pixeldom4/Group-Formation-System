package usecase.getprojects;

/**
 * This is an immutable data structure responsible for storing the output
 * data related to retrieving projects.
 */
public class GetProjectsOutputData {
    private final Object[][] projects;

    /**
     * Constructs a GetProjectsOutputData object with the specified projects data.
     *
     * @param projects the projects data.
     */
    public GetProjectsOutputData(Object[][] projects) {
        this.projects = projects;
    }

    /**
     * Gets the projects data.
     *
     * @return the projects data.
     */
    public Object[][] getData() {
        return this.projects;
    }
}
