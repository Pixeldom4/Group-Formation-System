package usecase.createverification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the CreateVerificationController class.
 */
public class CreateVerificationControllerTest {

    private CreateVerificationInputBoundary inputBoundary;
    private CreateVerificationController controller;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        inputBoundary = mock(CreateVerificationInputBoundary.class);
        controller = new CreateVerificationController(inputBoundary);
    }

    /**
     * Tests that the createVerification method calls the createVerification method of the inputBoundary.
     */
    @Test
    public void testCreateVerification() {
        // Act
        controller.createVerification();

        // Assert
        verify(inputBoundary, times(1)).createVerification();
    }
}


