package usecase.manageapplication;

import org.junit.jupiter.api.Test;
import usecase.manageapplications.ManageApplicationsController;
import usecase.manageapplications.ManageApplicationsUseCaseFactory;
import viewmodel.DisplayProjectApplicationViewModel;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class ManageApplicationUseCaseFactoryTest {

    @Test
    void testCreateController(){
        DisplayProjectApplicationViewModel displayProjectApplicationViewModel = mock(DisplayProjectApplicationViewModel.class);

        ManageApplicationsController controller = ManageApplicationsUseCaseFactory.createController(displayProjectApplicationViewModel);

        assertNotNull(controller);
    }
}
