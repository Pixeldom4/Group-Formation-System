package usecase.createapplication;

import org.junit.jupiter.api.Test;
import usecase.manageapplications.createapplication.CreateApplicationOutputData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Unit tests for the CreateApplicationOutputData class.
 */
public class CreateApplicationOutputDataTest {

    /**
     * Tests that the sender user ID is stored correctly in the output data.
     */
    @Test
    void createApplicationOutputDataStoresCorrectSenderUserId() {
        CreateApplicationOutputData outputData = new CreateApplicationOutputData(1, 2, true);
        assertEquals(1, outputData.getSenderUserId());
    }

    /**
     * Tests that the project ID is stored correctly in the output data.
     */
    @Test
    void createApplicationOutputDataStoresCorrectProjectId() {
        CreateApplicationOutputData outputData = new CreateApplicationOutputData(1, 2, true);
        assertEquals(2, outputData.getProjectId());
    }

    /**
     * Tests that the success status is stored correctly in the output data.
     */
    @Test
    void createApplicationOutputDataStoresSuccessStatus() {
        CreateApplicationOutputData outputData = new CreateApplicationOutputData(1, 2, true);
        assertTrue(outputData.isSuccess());
    }

    /**
     * Tests that the failure status is stored correctly in the output data.
     */
    @Test
    void createApplicationOutputDataStoresFailureStatus() {
        CreateApplicationOutputData outputData = new CreateApplicationOutputData(1, 2, false);
        assertFalse(outputData.isSuccess());
    }
}