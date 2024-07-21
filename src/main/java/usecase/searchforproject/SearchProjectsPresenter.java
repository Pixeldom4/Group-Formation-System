package usecase.searchforproject;

import entities.ProjectInterface;
import viewmodel.SearchPanelViewModel;

import java.util.ArrayList;

/**
 * Presenter class for searching projects.
 * Implements the output boundary to present the search results.
 */
public class SearchProjectsPresenter implements SearchProjectOutputBoundary {
    private final SearchPanelViewModel searchPanelViewModel;

    /**
     * Constructs a SearchProjectsPresenter with the specified view model.
     *
     * @param viewModel the view model to update with search results.
     */
    public SearchProjectsPresenter(SearchPanelViewModel viewModel) {
        this.searchPanelViewModel = viewModel;
    }

    /**
     * Sends a list of projects to be presented to the presenter.
     *
     * @param projects the list of projects to be presented.
     */
    public void presentProjects(ArrayList<ProjectInterface> projects) {
        searchPanelViewModel.setProjects(projects);
        searchPanelViewModel.firePropertyChanged();
    }
}
