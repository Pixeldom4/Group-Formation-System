package usecase.createproject;

import org.junit.jupiter.api.Test;
import usecase.manageprojects.createproject.CreateProjectController;
import usecase.manageprojects.createproject.CreateProjectUseCaseFactory;
import viewmodel.AddProjectPanelViewModel;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the CreateProjectUseCaseFactory class.
 */
public class CreateProjectUseCaseFactoryTest {

    /**
     * Tests that the createController method returns a non-null controller.
     */
    @Test
    void createControllerReturnsNonNullController() {
        AddProjectPanelViewModel mockViewModel = mock(AddProjectPanelViewModel.class);
        CreateProjectController controller = CreateProjectUseCaseFactory.createController(mockViewModel);

        assertNotNull(controller);
    }
}