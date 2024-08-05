package usecase.manageapplications.getapplications;

/**
 * Controller class for retrieving applications.
 * Interacts with the input boundary to process retrieving applications for a project.
 */
public class GetApplicationsController {
    private final GetApplicationsInputBoundary manageApplicationsInteractor;

    /**
     * Constructs a GetApplicationsController.
     *
     * @param manageApplicationsInteractor the interactor that handles the get applications use case.
     */
    public GetApplicationsController(GetApplicationsInputBoundary manageApplicationsInteractor) {
        this.manageApplicationsInteractor = manageApplicationsInteractor;
    }

    /**
     * Retrieves applications for a specific project.
     *
     * @param projectId the ID of the project.
     */
    public void getApplicationsForProject(int projectId) {
        GetApplicationsInputData inputData = new GetApplicationsInputData(projectId);
        manageApplicationsInteractor.getApplicationsForProject(inputData);
    }
}
