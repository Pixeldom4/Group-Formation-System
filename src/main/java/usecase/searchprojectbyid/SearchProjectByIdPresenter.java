package usecase.searchprojectbyid;

import entities.ProjectInterface;
import viewmodel.SearchPanelViewModel;

import java.util.ArrayList;

public class SearchProjectByIdPresenter implements SearchProjectByIdOutputBoundary {
    private final SearchPanelViewModel searchPanelViewModel;

    /**
     * Constructor for the SearchProjectByIdPresenter class.
     *
     * @param viewModel The view model for the search panel.
     */
    public SearchProjectByIdPresenter(SearchPanelViewModel viewModel) {
        this.searchPanelViewModel = viewModel;
    }

    /**
     * Presents the project to the search panel.
     *
     * @param project The project to present.
     */
    @Override
    public void presentProject(ProjectInterface project) {
        ArrayList<ProjectInterface> projects = new ArrayList<>();
        projects.add(project);

        searchPanelViewModel.setProjects(projects);
        searchPanelViewModel.firePropertyChanged();
    }
}
