package usecase.searchforproject;

/**
 * Controller class for searching projects.
 * Interacts with the input boundary to process project search.
 */
public class SearchProjectController {

    private final SearchProjectInputBoundary interactor;

    /**
     * Constructs a SearchProjectController.
     *
     * @param interactor the interactor that handles the search project use case.
     */
    public SearchProjectController(SearchProjectInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Searches for projects based on the given keywords.
     *
     * @param keywords the keywords to search for.
     */
    public void searchProjects(String keywords) {
        interactor.searchProjects(keywords);
    }
}
