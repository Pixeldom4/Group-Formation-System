package usecase.acceptapplication;

import org.junit.jupiter.api.Test;
import usecase.manageapplications.acceptapplication.AcceptApplicationController;
import usecase.manageapplications.acceptapplication.AcceptApplicationUseCaseFactory;
import viewmodel.DisplayProjectApplicationViewModel;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class AcceptApplicationUseCaseFactoryTest {

    @Test
    void testCreateUseCase() {
        DisplayProjectApplicationViewModel viewModel = mock(DisplayProjectApplicationViewModel.class);
        AcceptApplicationController controller = AcceptApplicationUseCaseFactory.createController(viewModel);
        assertNotNull(controller);
    }
}
