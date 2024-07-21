package usecase.searchforuser;

import view.SearchPanel;
import viewmodel.SearchPanelViewModel;

public class SearchUserUseCaseFactory {
    private SearchUserUseCaseFactory() {
    }

    /**
     * Creates a search project by ID controller.
     *
     * @param searchPanelViewModel The view model for the search panel.
     *
     * @return The search project by ID controller.
     */
    public static SearchUserController createSearchUserController(SearchPanelViewModel searchPanelViewModel){
        SearchUserOutputBoundary presenter = new SearchUserPresenter(searchPanelViewModel);
        SearchUserInputBoundary interactor = new SearchUserInteractor(presenter);
        return new SearchUserController(interactor);
    }
}
