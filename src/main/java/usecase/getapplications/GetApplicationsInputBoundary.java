package usecase.getapplications;

/**
 * Input boundary interface for retrieving applications.
 * Defines the method to get applications for a project.
 */
public interface GetApplicationsInputBoundary {
    /**
     * Retrieves applications for a project.
     *
     * @param inputData the input data containing the project ID.
     */
    void getApplicationsForProject(GetApplicationsInputData inputData);
}
