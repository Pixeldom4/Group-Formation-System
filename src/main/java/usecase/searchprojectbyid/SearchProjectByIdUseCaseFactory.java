package usecase.searchprojectbyid;

import view.SearchPanel;
import viewmodel.SearchPanelViewModel;

/**
 * Factory class for creating instances related to the Search Project By ID use case.
 */
public class SearchProjectByIdUseCaseFactory {
    /**
     * Private constructor to prevent instantiation.
     */
    private SearchProjectByIdUseCaseFactory() {
    }

    /**
     * Creates a search project by ID controller.
     *
     * @param searchPanelViewModel The view model for the search panel.
     * @return The search project by ID controller.
     */
    public static SearchProjectByIdController createSearchProjectByIdController(SearchPanelViewModel searchPanelViewModel){
        SearchProjectByIdOutputBoundary presenter = new SearchProjectByIdPresenter(searchPanelViewModel);
        SearchProjectByIdInputBoundary interactor = new SearchProjectByIdInteractor(presenter);
        return new SearchProjectByIdController(interactor);
    }
}
