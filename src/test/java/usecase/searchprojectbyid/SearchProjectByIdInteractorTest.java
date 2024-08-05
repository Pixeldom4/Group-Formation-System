package usecase.searchprojectbyid;

import dataaccess.IProjectRepository;
import entities.Project;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * Unit tests for the SearchProjectByIdInteractor class.
 */
public class SearchProjectByIdInteractorTest {

    /**
     * Tests that searchProjectById returns the correct project when the ID exists in the repository.
     */
    @Test
    void searchProjectByIdReturnsCorrectProject() {
        IProjectRepository mockRepository = mock(IProjectRepository.class);
        Project mockProject = mock(Project.class);
        when(mockRepository.getProjectById(1)).thenReturn(mockProject);
        SearchProjectByIdOutputBoundary mockPresenter = mock(SearchProjectByIdOutputBoundary.class);

        SearchProjectByIdInteractor interactor = new SearchProjectByIdInteractor(mockPresenter, mockRepository);
        interactor.searchProjectById(1);

        verify(mockPresenter).presentProject(mockProject);
    }

    /**
     * Tests that searchProjectById returns null when the ID does not exist in the repository.
     */
    @Test
    void searchProjectByIdReturnsNullForNonExistentId() {
        IProjectRepository mockRepository = mock(IProjectRepository.class);
        when(mockRepository.getProjectById(999)).thenReturn(null);
        SearchProjectByIdOutputBoundary mockPresenter = mock(SearchProjectByIdOutputBoundary.class);

        SearchProjectByIdInteractor interactor = new SearchProjectByIdInteractor(mockPresenter, mockRepository);
        interactor.searchProjectById(999);

        verify(mockPresenter).presentProject(null);
    }

    /**
     * Tests that searchProjectById handles an ID of zero.
     */
    @Test
    void searchProjectByIdHandlesZeroId() {
        IProjectRepository mockRepository = mock(IProjectRepository.class);
        when(mockRepository.getProjectById(0)).thenReturn(null);
        SearchProjectByIdOutputBoundary mockPresenter = mock(SearchProjectByIdOutputBoundary.class);

        SearchProjectByIdInteractor interactor = new SearchProjectByIdInteractor(mockPresenter, mockRepository);
        interactor.searchProjectById(0);

        verify(mockPresenter).presentProject(null);
    }

    /**
     * Tests that searchProjectById handles a negative ID.
     */
    @Test
    void searchProjectByIdHandlesNegativeId() {
        IProjectRepository mockRepository = mock(IProjectRepository.class);
        when(mockRepository.getProjectById(-1)).thenReturn(null);
        SearchProjectByIdOutputBoundary mockPresenter = mock(SearchProjectByIdOutputBoundary.class);

        SearchProjectByIdInteractor interactor = new SearchProjectByIdInteractor(mockPresenter, mockRepository);
        interactor.searchProjectById(-1);

        verify(mockPresenter).presentProject(null);
    }
}