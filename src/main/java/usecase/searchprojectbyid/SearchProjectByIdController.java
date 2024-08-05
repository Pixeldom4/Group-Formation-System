package usecase.searchprojectbyid;

/**
 * Controller for handling the search project by ID use case.
 */
public class SearchProjectByIdController {
    private final SearchProjectByIdInputBoundary interactor;

    /**
     * Constructor for the SearchProjectByIdController class.
     *
     * @param interactor The interactor to be used.
     */
    public SearchProjectByIdController(SearchProjectByIdInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Search for a project by its ID.
     *
     * @param projectId The ID of the project to search for.
     */
    public void searchProjectById(int projectId) {
        interactor.searchProjectById(projectId);
    }

    /**
     * Get the interactor.
     *
     * @return The interactor used by this controller.
     */
    public SearchProjectByIdInputBoundary getInteractor() {
        return this.interactor;
    }
}