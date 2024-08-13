package usecase.searchforproject;

import api.embeddingapi.EmbeddingAPIInterface;
import api.embeddingapi.OpenAPIDataEmbed;
import config.DataAccessConfig;
import dataaccess.IProjectRepository;
import viewmodel.SearchPanelViewModel;

/**
 * Factory class for creating instances of the SearchProject use case.
 */
public class SearchProjectUseCaseFactory {
    private static final IProjectRepository projectRepository = DataAccessConfig.getProjectRepository();
    private static final EmbeddingAPIInterface embeddingAPI = new OpenAPIDataEmbed();

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
        SearchProjectInputBoundary interactor = new SearchProjectsInteractor(presenter, projectRepository, embeddingAPI);
        return new SearchProjectController(interactor);
    }
}
