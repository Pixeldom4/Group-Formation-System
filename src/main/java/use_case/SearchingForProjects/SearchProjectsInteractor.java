package use_case.SearchingForProjects;
import java.util.ArrayList;

import Entities.ProjectInterface;
import data_access.DAOImplementationConfig;
import data_access.LocalProjectSearchObject;
import data_access.ProjectDataAccessInterface;
import data_access.ProjectSearchInterface;

public class SearchProjectsInteractor implements SearchProjectInputBoundary {
    private final ProjectSearchInterface projectDAO = new LocalProjectSearchObject();
    private final ProjectDataAccessInterface projectDataAccess = DAOImplementationConfig.getProjectDataAccess();
    private SearchProjectOutputBoundary presenter;

    public SearchProjectsInteractor(SearchProjectOutputBoundary presenter) {
        this.presenter = presenter;
    }

    public void searchProjects(String keywords) {
        int projectNum = projectDataAccess.numberOfProjects();
        if (projectNum < 10) {
            searchProjects(keywords, projectNum);
        }
        else {
            searchProjects(keywords, 10);
        }
    }

    @Override
    public void searchProjects(String keywords, int limit) {
        ArrayList<ProjectInterface> projects = projectDAO.searchProjects(keywords, limit);
        presenter.presentProjects(projects);
    }
}

