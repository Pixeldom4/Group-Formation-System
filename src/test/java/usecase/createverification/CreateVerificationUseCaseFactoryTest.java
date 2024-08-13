package usecase.createverification;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateVerificationUseCaseFactoryTest {

    @Test
    public void checkFactoryReturnsController() {
        CreateVerificationViewModel viewModel = mock(CreateVerificationViewModel.class);
        CreateVerificationController controller = CreateVerificationUseCaseFactory.createController(viewModel);
        assertNotNull(controller);
    }

}