package Presenters;

import Entities.Project;
import view.SearchPanel;

import java.util.List;

public class SearchProjectsPresenter {
    private SearchPanel view;

    public SearchProjectsPresenter(SearchPanel view) {
        this.view = view;
    }

    public void presentProjects(List<Project> projects) {
        view.displayProjects(projects);
    }
}

