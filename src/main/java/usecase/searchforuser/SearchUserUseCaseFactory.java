package usecase.searchforuser;

import viewmodel.SearchPanelViewModel;

/**
 * Factory class for creating instances of the SearchUser use case.
 */
public class SearchUserUseCaseFactory {
    private SearchUserUseCaseFactory() {
    }

    /**
     * Creates a search user by email controller.
     *
     * @param searchPanelViewModel The view model for the search panel.
     * @return The search user by email controller.
     */
    public static SearchUserController createSearchUserController(SearchPanelViewModel searchPanelViewModel) {
        SearchUserOutputBoundary presenter = new SearchUserPresenter(searchPanelViewModel);
        SearchUserInputBoundary interactor = new SearchUserInteractor(presenter);
        return new SearchUserController(interactor);
    }
}
