package usecase.createverification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the CreateVerificationInteractor class.
 */
public class CreateVerificationInteractorTest {

    private CreateVerificationOutputBoundary outputBoundary;
    private ImageFinderInterface imageFinder;
    private CreateVerificationInteractor interactor;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        outputBoundary = mock(CreateVerificationOutputBoundary.class);
        imageFinder = mock(ImageFinderInterface.class);
        interactor = new CreateVerificationInteractor(outputBoundary, imageFinder);
    }

    /**
     * Tests that the createVerification method correctly interacts with the outputBoundary and imageFinder.
     */
    @Test
    public void testCreateVerification() {
        // Arrange
        String expectedImageLocation = "verifyimages/testImage.png";
        when(imageFinder.findImage("verifyimages/")).thenReturn(expectedImageLocation);

        // Act
        interactor.createVerification();

        // Assert
        verify(imageFinder).findImage("verifyimages/");
        verify(outputBoundary).verificationCreated(argThat(outputData ->
                expectedImageLocation.equals(outputData.getVerificationImageLocation())
        ));
    }
}
