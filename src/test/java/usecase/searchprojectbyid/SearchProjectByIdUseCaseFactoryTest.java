package usecase.searchprojectbyid;

import org.junit.jupiter.api.Test;
import viewmodel.SearchPanelViewModel;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the SearchProjectByIdUseCaseFactory class.
 */
public class SearchProjectByIdUseCaseFactoryTest {

    /**
     * Tests that createSearchProjectByIdController returns a non-null controller.
     */
    @Test
    void createSearchProjectByIdControllerReturnsController() {
        SearchPanelViewModel mockViewModel = mock(SearchPanelViewModel.class);
        SearchProjectByIdController controller = SearchProjectByIdUseCaseFactory.createSearchProjectByIdController(mockViewModel);

        assertNotNull(controller);
    }

    /**
     * Tests that createSearchProjectByIdController initializes the presenter.
     */
    @Test
    void createSearchProjectByIdControllerInitializesPresenter() {
        SearchPanelViewModel mockViewModel = mock(SearchPanelViewModel.class);
        SearchProjectByIdController controller = SearchProjectByIdUseCaseFactory.createSearchProjectByIdController(mockViewModel);

        SearchProjectByIdInteractor interactor = (SearchProjectByIdInteractor) controller.getInteractor();
        SearchProjectByIdPresenter presenter = (SearchProjectByIdPresenter) interactor.getPresenter();

        assertNotNull(presenter);
    }

    /**
     * Tests that createSearchProjectByIdController initializes the interactor.
     */
    @Test
    void createSearchProjectByIdControllerInitializesInteractor() {
        SearchPanelViewModel mockViewModel = mock(SearchPanelViewModel.class);
        SearchProjectByIdController controller = SearchProjectByIdUseCaseFactory.createSearchProjectByIdController(mockViewModel);

        SearchProjectByIdInteractor interactor = (SearchProjectByIdInteractor) controller.getInteractor();

        assertNotNull(interactor);
    }
}