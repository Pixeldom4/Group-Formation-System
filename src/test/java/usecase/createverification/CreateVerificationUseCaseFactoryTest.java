package usecase.createverification;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateVerificationUseCaseFactoryTest {

    @Test
    public void createController_withValidViewModel_returnsController() {
        CreateVerificationViewModel viewModel = mock(CreateVerificationViewModel.class);
        CreateVerificationController controller = CreateVerificationUseCaseFactory.createController(viewModel);
        assertNotNull(controller);
    }

}