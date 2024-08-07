package usecase.searchforproject;

import api.embeddingapi.EmbeddingAPIInterface;
import entities.ProjectInterface;
import dataaccess.IProjectRepository;

import java.util.ArrayList;

/**
 * Interactor class for searching projects.
 * Implements the input boundary to handle project search logic.
 */
public class SearchProjectsInteractor implements SearchProjectInputBoundary {
    private final ProjectSearchInterface projectDAO;
    private final SearchProjectOutputBoundary presenter;

    /**
     * Creates a new SearchProjectsInteractor using the given project repository.
     * Used for testing where files are not stored in the project folder.
     *
     * @param presenter the output boundary.
     * @param projectRepository the project repository to use.
     */
    public SearchProjectsInteractor(SearchProjectOutputBoundary presenter, IProjectRepository projectRepository,
                                    EmbeddingAPIInterface embeddingAPI) {
        this.presenter = presenter;
        this.projectDAO = new LocalProjectSearchObject(projectRepository, embeddingAPI);
    }

    /**
     * Searches for projects based on the given keywords.
     *
     * @param keywords the keywords to search for.
     */
    @Override
    public void searchProjects(String keywords) {
        searchProjects(keywords, 10);
    }

    /**
     * Searches for projects based on the given keywords with a limit.
     *
     * @param keywords the keywords to search for.
     * @param limit the maximum number of projects to return.
     */
    private void searchProjects(String keywords, int limit) {
        ArrayList<ProjectInterface> projects = projectDAO.searchProjects(keywords, limit);
        presenter.presentProjects(projects);
    }
}
