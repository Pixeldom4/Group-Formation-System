package usecase.getprojects;

/**
 * Input boundary interface for retrieving projects.
 * Defines the method to get projects for the logged-in user.
 */
public interface GetProjectsInputBoundary {
    /**
     * Retrieves projects for the logged-in user.
     *
     * @param inputData the input data required to retrieve projects.
     */
    void getProjects(GetProjectsInputData inputData);
}
