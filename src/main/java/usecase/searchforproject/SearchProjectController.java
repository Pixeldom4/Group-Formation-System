package usecase.searchforproject;

public class SearchProjectController {

    private final SearchProjectInputBoundary interactor;

    public SearchProjectController(SearchProjectInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void searchProjects(String keywords) {
        interactor.searchProjects(keywords);
    }
}
