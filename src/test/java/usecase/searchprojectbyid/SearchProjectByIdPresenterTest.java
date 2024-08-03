package usecase.searchprojectbyid;

import entities.ProjectInterface;
import org.junit.jupiter.api.Test;
import viewmodel.SearchPanelViewModel;

import static org.mockito.Mockito.*;

/**
 * Unit tests for the SearchProjectByIdPresenter class.
 */
public class SearchProjectByIdPresenterTest {

    /**
     * Tests that presentProject updates the view model with the given project.
     */
    @Test
    void presentProjectUpdatesViewModelWithProject() {
        SearchPanelViewModel mockViewModel = mock(SearchPanelViewModel.class);
        ProjectInterface mockProject = mock(ProjectInterface.class);
        SearchProjectByIdPresenter presenter = new SearchProjectByIdPresenter(mockViewModel);

        presenter.presentProject(mockProject);

        verify(mockViewModel).setProjects(argThat(projects -> projects.size() == 1 && projects.contains(mockProject)));
        verify(mockViewModel).firePropertyChanged();
    }

    /**
     * Tests that presentProject handles a null project.
     */
    @Test
    void presentProjectHandlesNullProject() {
        SearchPanelViewModel mockViewModel = mock(SearchPanelViewModel.class);
        SearchProjectByIdPresenter presenter = new SearchProjectByIdPresenter(mockViewModel);

        presenter.presentProject(null);

        verify(mockViewModel).setProjects(argThat(projects -> projects.size() == 1 && projects.contains(null)));
        verify(mockViewModel).firePropertyChanged();
    }
}