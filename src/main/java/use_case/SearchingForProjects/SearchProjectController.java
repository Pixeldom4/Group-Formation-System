package use_case.SearchingForProjects;

public class SearchProjectController {

    private final SearchProjectsInteractor interactor;

    public SearchProjectController(SearchProjectsInteractor interactor) {
        this.interactor = interactor;
    }

    public void searchProjects(String keywords) {
        interactor.searchProjects(keywords);
    }
}
