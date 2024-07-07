package use_case.SearchingForProjects;

import data_access.ProjectDAO;

public class SearchProjectsInteractor {
    private ProjectDAO projectDAO;
    private SearchProjectsPresenter presenter;

    public SearchProjectsInteractor(ProjectDAO projectDAO, SearchProjectsPresenter presenter) {
        this.projectDAO = projectDAO;
        this.presenter = presenter;
    }

    public void searchProjects(String keywords) {
        List<Project> projects = projectDAO.MatchedProjects(keywords);
        presenter.presentProjects(projects);
    }
}

