package usecase.getprojects;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import usecase.manageprojects.getprojects.GetProjectsController;
import usecase.manageprojects.getprojects.GetProjectsUseCaseFactory;
import viewmodel.MyProjectsPanelViewModel;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Tests for the GetProjectsUseCaseFactory class.
 */
public class GetProjectsUseCaseFactoryTest {

    /**
     * Tests the createGetProjectsController method to ensure it returns a valid GetProjectsController instance.
     */
    @Test
    public void testCreateGetProjectsController() {
        MyProjectsPanelViewModel myProjectsPanelViewModel = mock(MyProjectsPanelViewModel.class);

        GetProjectsController controller = GetProjectsUseCaseFactory.createGetProjectsController(myProjectsPanelViewModel);

        assertNotNull(controller);
    }
}
