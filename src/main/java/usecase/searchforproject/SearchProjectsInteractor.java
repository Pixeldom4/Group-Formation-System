package usecase.searchforproject;
import java.util.ArrayList;

import entities.ProjectInterface;
import dataaccess.IProjectRepository;

public class SearchProjectsInteractor implements SearchProjectInputBoundary {
    private ProjectSearchInterface projectDAO = new LocalProjectSearchObject();
    private SearchProjectOutputBoundary presenter;

    public SearchProjectsInteractor(SearchProjectOutputBoundary presenter) {
        this.presenter = presenter;
    }

    /**
     * Creates a new SearchProjectsInteractor using the given project repository.
     * Used for testing where files are not stored in the project folder.
     * @param presenter the output boundary
     * @param projectRepository the project repository to use
     */
    public SearchProjectsInteractor(SearchProjectOutputBoundary presenter, IProjectRepository projectRepository) {
        this.presenter = presenter;
        this.projectDAO = new LocalProjectSearchObject(projectRepository);
    }

    @Override
    public void searchProjects(String keywords) {
        searchProjects(keywords, 10);
    }

    private void searchProjects(String keywords, int limit) {
        ArrayList<ProjectInterface> projects = projectDAO.searchProjects(keywords, limit);
        presenter.presentProjects(projects);
    }
}

