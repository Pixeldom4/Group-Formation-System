package usecase.createverification;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the CreateVerificationUseCaseFactory class.
 */
public class CreateVerificationUseCaseFactoryTest {

    /**
     * Tests that the createController method creates a non-null CreateVerificationController.
     */
    @Test
    public void testCreateController() {
        // Arrange
        CreateVerificationViewModel viewModel = mock(CreateVerificationViewModel.class);

        // Act
        CreateVerificationController controller = CreateVerificationUseCaseFactory.createController(viewModel);

        // Assert
        assertNotNull(controller);
    }
}

