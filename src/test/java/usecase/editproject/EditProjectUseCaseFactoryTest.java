package usecase.editproject;

import org.junit.jupiter.api.Test;
import usecase.manageprojects.editproject.EditProjectController;
import usecase.manageprojects.editproject.EditProjectUseCaseFactory;
import viewmodel.EditProjectPanelViewModel;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class EditProjectUseCaseFactoryTest {

    /**
     * Test the creation of an edit project controller.
     */
    @Test
    public void testCreateController() {
        EditProjectPanelViewModel viewModel = mock(EditProjectPanelViewModel.class);
        EditProjectController controller = EditProjectUseCaseFactory.createController(viewModel);
        assertNotNull(controller);
    }
}
