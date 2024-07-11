package usecase.searchforproject;

import entities.ProjectInterface;
import viewmodel.SearchPanelViewModel;

import java.util.ArrayList;

public class SearchProjectsPresenter implements SearchProjectOutputBoundary {
    private final SearchPanelViewModel searchPanelViewModel;

    public SearchProjectsPresenter(SearchPanelViewModel viewModel) {
        this.searchPanelViewModel = viewModel;
    }

    public void presentProjects(ArrayList<ProjectInterface> projects) {

        searchPanelViewModel.setProjects(projects);
        searchPanelViewModel.firePropertyChanged();
    }
}

