package usecase.manageprojects;

import org.junit.jupiter.api.Test;
import viewmodel.AddProjectPanelViewModel;
import viewmodel.EditProjectPanelViewModel;
import viewmodel.MyProjectsPanelViewModel;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class ManageProjectsUseCaseFactoryTest {

    @Test
    void testCreateController(){
        AddProjectPanelViewModel addProjectPanelViewModel = mock(AddProjectPanelViewModel.class);
        EditProjectPanelViewModel editProjectPanelViewModel = mock(EditProjectPanelViewModel.class);
        MyProjectsPanelViewModel myProjectsPanelViewModel = mock(MyProjectsPanelViewModel.class);
        ManageProjectsController controller = ManageProjectsUseCaseFactory.createController(addProjectPanelViewModel,
                                                                                            editProjectPanelViewModel,
                                                                                            myProjectsPanelViewModel);

        assertNotNull(controller);
    }
}
