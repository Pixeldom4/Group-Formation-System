package usecase.searchforproject;

import dataaccess.DataAccessConfig;
import dataaccess.IProjectRepository;
import viewmodel.SearchPanelViewModel;

/**
 * Factory class for creating instances of the SearchProject use case.
 */
public class SearchProjectUseCaseFactory {
    private static final IProjectRepository projectRepository = DataAccessConfig.getProjectRepository();

    // Private constructor to prevent instantiation
    private SearchProjectUseCaseFactory() {}

    /**
     * Creates a search project controller for search project use case.
     *
     * @param searchPanelViewModel the view model of the search panel.
     * @return the search project controller.
     */
    public static SearchProjectController createSearchProjectController(SearchPanelViewModel searchPanelViewModel) {
        SearchProjectOutputBoundary presenter = new SearchProjectsPresenter(searchPanelViewModel);
        SearchProjectInputBoundary interactor = new SearchProjectsInteractor(presenter, projectRepository);
        return new SearchProjectController(interactor);
    }
}
