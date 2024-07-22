package usecase.getprojects;

/**
 * Controller class for retrieving projects.
 * Interacts with the input boundary to process project retrieval.
 */
public class GetProjectsController {
    private final GetProjectsInputBoundary getProjectsInteractor;

    /**
     * Constructs a GetProjectsController.
     *
     * @param getProjectsInteractor the interactor that handles the get projects use case.
     */
    public GetProjectsController(GetProjectsInputBoundary getProjectsInteractor) {
        this.getProjectsInteractor = getProjectsInteractor;
    }

    /**
     * Retrieves projects for the logged-in user.
     */
    public void getProjects(int userId) {
        GetProjectsInputData inputData = new GetProjectsInputData(userId);
        getProjectsInteractor.getProjects(inputData);
    }
}
