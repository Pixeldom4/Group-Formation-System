package use_case.SearchingForProjects;
import java.util.ArrayList;

import Entities.ProjectInterface;
import data_access.LocalProjectSearchObject;
import data_access.ProjectSearchInterface;

public class SearchProjectsInteractor implements SearchProjectInputBoundary {
    private final ProjectSearchInterface projectDAO = new LocalProjectSearchObject();
    private SearchProjectsPresenter presenter;

    public SearchProjectsInteractor(SearchProjectsPresenter presenter) {
        this.presenter = presenter;
    }

    public void searchProjects(String keywords) {
        ArrayList<ProjectInterface> projects = projectDAO.searchProjects(keywords, 10);
        presenter.presentProjects(projects);
    }
}

