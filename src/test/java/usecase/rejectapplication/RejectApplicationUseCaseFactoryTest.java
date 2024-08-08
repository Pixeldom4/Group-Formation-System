package usecase.rejectapplication;

import org.junit.jupiter.api.Test;
import usecase.manageapplications.rejectapplication.RejectApplicationController;
import usecase.manageapplications.rejectapplication.RejectApplicationUseCaseFactory;
import viewmodel.DisplayProjectApplicationViewModel;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class RejectApplicationUseCaseFactoryTest {

    @Test
    void testCreateUseCase() {
        DisplayProjectApplicationViewModel viewModel = mock(DisplayProjectApplicationViewModel.class);
        RejectApplicationController controller = RejectApplicationUseCaseFactory.createController(viewModel);
        assertNotNull(controller);
    }
}
