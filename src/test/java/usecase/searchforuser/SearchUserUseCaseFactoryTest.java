package usecase.searchforuser;

import org.junit.jupiter.api.Test;
import viewmodel.SearchPanelViewModel;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the SearchUserUseCaseFactory class.
 */
public class SearchUserUseCaseFactoryTest {

    /**
     * Tests that createSearchUserController returns a controller with the correct dependencies.
     */
    @Test
    void createSearchUserControllerReturnsControllerWithCorrectDependencies() {
        SearchPanelViewModel mockViewModel = mock(SearchPanelViewModel.class);
        SearchUserController controller = SearchUserUseCaseFactory.createSearchUserController(mockViewModel);

        assertNotNull(controller);
        assertNotNull(controller.getInteractor());
        assertTrue(controller.getInteractor() instanceof SearchUserInteractor);
    }

    /**
     * Tests that createSearchUserController initializes the presenter correctly.
     */
    @Test
    void createSearchUserControllerInitializesPresenterCorrectly() {
        SearchPanelViewModel mockViewModel = mock(SearchPanelViewModel.class);
        SearchUserController controller = SearchUserUseCaseFactory.createSearchUserController(mockViewModel);

        SearchUserInteractor interactor = (SearchUserInteractor) controller.getInteractor();
        assertNotNull(interactor.getPresenter());
        assertTrue(interactor.getPresenter() instanceof SearchUserPresenter);
    }

    /**
     * Tests that createSearchUserController initializes the interactor correctly.
     */
    @Test
    void createSearchUserControllerInitializesInteractorCorrectly() {
        SearchPanelViewModel mockViewModel = mock(SearchPanelViewModel.class);
        SearchUserController controller = SearchUserUseCaseFactory.createSearchUserController(mockViewModel);

        SearchUserInteractor interactor = (SearchUserInteractor) controller.getInteractor();
        assertNotNull(interactor.getRepository());
    }
}