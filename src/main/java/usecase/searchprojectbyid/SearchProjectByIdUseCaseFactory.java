package usecase.searchprojectbyid;

import dataaccess.DataAccessConfig;
import dataaccess.IProjectRepository;
import view.SearchPanel;
import viewmodel.SearchPanelViewModel;

/**
 * Factory class for creating instances related to the Search Project By ID use case.
 */
public class SearchProjectByIdUseCaseFactory {
    private static final IProjectRepository projectRepository = DataAccessConfig.getProjectRepository();

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
        SearchProjectByIdInputBoundary interactor = new SearchProjectByIdInteractor(presenter, projectRepository);
        return new SearchProjectByIdController(interactor);
    }
}
