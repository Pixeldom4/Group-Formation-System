package usecase.searchprojectbyid;

import view.SearchPanel;
import viewmodel.SearchPanelViewModel;

public class SearchProjectByIdUseCaseFactory {
    private SearchProjectByIdUseCaseFactory() {
    }

    /**
     * Creates a search project by ID controller.
     *
     * @param searchPanelViewModel The view model for the search panel.
     *
     * @return The search project by ID controller.
     */
    public static SearchProjectByIdController createSearchProjectByIdController(SearchPanelViewModel searchPanelViewModel){
        SearchProjectByIdOutputBoundary presenter = new SearchProjectByIdPresenter(searchPanelViewModel);
        SearchProjectByIdInputBoundary interactor = new SearchProjectByIdInteractor(presenter);
        return new SearchProjectByIdController(interactor);
    }
}
