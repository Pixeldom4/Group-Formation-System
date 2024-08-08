package usecase.manageapplication.createapplication.createapplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import viewmodel.SearchPanelViewModel;
import usecase.manageapplications.createapplication.CreateApplicationController;
import usecase.manageapplications.createapplication.CreateApplicationInteractor;
import usecase.manageapplications.createapplication.CreateApplicationOutputBoundary;
import usecase.manageapplications.createapplication.CreateApplicationPresenter;
import usecase.manageapplications.createapplication.CreateApplicationUseCaseFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

/**
 * Unit tests for the CreateApplicationUseCaseFactory class.
 */
public class CreateApplicationUseCaseFactoryTest {

    private SearchPanelViewModel mockViewModel;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        mockViewModel = mock(SearchPanelViewModel.class);
    }

    /**
     * Tests that the createController method returns a non-null controller.
     */
    @Test
    void createControllerReturnsNonNullController() {
        CreateApplicationController controller = CreateApplicationUseCaseFactory.createController(mockViewModel);
        assertNotNull(controller);
    }

    /**
     * Tests that the createController method initializes the presenter correctly.
     */
    @Test
    void createControllerInitializesPresenterCorrectly() {
        CreateApplicationController controller = CreateApplicationUseCaseFactory.createController(mockViewModel);
        CreateApplicationInteractor interactor = (CreateApplicationInteractor) controller.getInteractor();
        CreateApplicationOutputBoundary presenter = interactor.getPresenter();
        assertNotNull(presenter);
        assert(presenter instanceof CreateApplicationPresenter);
    }

    /**
     * Tests that the createController method initializes the interactor correctly.
     */
    @Test
    void createControllerInitializesInteractorCorrectly() {
        CreateApplicationController controller = CreateApplicationUseCaseFactory.createController(mockViewModel);
        assertNotNull(controller.getInteractor());
        assert(controller.getInteractor() instanceof CreateApplicationInteractor);
    }
}