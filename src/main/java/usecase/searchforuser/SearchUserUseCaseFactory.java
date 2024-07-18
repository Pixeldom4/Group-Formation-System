package usecase.searchforuser;

import view.SearchPanel;
import viewmodel.SearchPanelViewModel;

public class SearchUserUseCaseFactory {
    private SearchUserUseCaseFactory() {
    }

    /**
     * Creates a search panel for searching users by email use case.
     *
     * @param searchPanelViewModel The view model for the search panel.
     *
     * @return The search panel.
     */
    public static SearchPanel createSearchUserPanel(SearchPanelViewModel searchPanelViewModel) {
        SearchUserController searchUserController = createSearchUserController(searchPanelViewModel);
        return new SearchPanel(searchPanelViewModel, searchUserController);
    }

    /**
     * Creates a search project by ID controller.
     *
     * @param searchPanelViewModel The view model for the search panel.
     *
     * @return The search project by ID controller.
     */
    private static SearchUserController createSearchUserController(SearchPanelViewModel searchPanelViewModel){
        SearchUserOutputBoundary presenter = new SearchUserPresenter(searchPanelViewModel);
        SearchUserInputBoundary interactor = new SearchUserInteractor(presenter);
        return new SearchUserController(interactor);
    }
}
