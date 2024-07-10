package use_case.SearchingForProjects;
import java.util.ArrayList;

import Entities.ProjectInterface;
import data_access.DAOImplementationConfig;
import data_access.IProjectRepository;

public class SearchProjectsInteractor implements SearchProjectInputBoundary {
    private final ProjectSearchInterface projectDAO = new LocalProjectSearchObject();
    private final IProjectRepository projectDataAccess = DAOImplementationConfig.getProjectDataAccess();
    private SearchProjectOutputBoundary presenter;

    public SearchProjectsInteractor(SearchProjectOutputBoundary presenter) {
        this.presenter = presenter;
    }

    public void searchProjects(String keywords) {
        searchProjects(keywords, 10);
    }

    @Override
    public void searchProjects(String keywords, int limit) {
        ArrayList<ProjectInterface> projects = projectDAO.searchProjects(keywords, limit);
        presenter.presentProjects(projects);
    }
}

