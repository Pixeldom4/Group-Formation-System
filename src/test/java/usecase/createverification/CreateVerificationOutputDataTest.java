package usecase.createverification;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the CreateVerificationOutputData class.
 */
public class CreateVerificationOutputDataTest {

    /**
     * Tests the getVerificationImageLocation method.
     */
    @Test
    public void testGetVerificationImageLocation() {
        // Arrange
        String expectedImageLocation = "verifyimages/testImage.png";
        CreateVerificationOutputData outputData = new CreateVerificationOutputData(expectedImageLocation);

        // Act
        String actualImageLocation = outputData.getVerificationImageLocation();

        // Assert
        assertEquals(expectedImageLocation, actualImageLocation);
    }
}


