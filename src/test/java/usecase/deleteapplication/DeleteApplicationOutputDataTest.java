package usecase.deleteapplication;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the DeleteApplicationOutputData class and related functionality.
 */
public class DeleteApplicationOutputDataTest {

    /**
     * Tests that the application is deleted successfully.
     */
    @Test
    void deleteApplicationSuccessfully() {
        int senderUserId = 1;
        int projectId = 1;

        DeleteApplicationController deleteApplicationController = new DeleteApplicationController(mock(DeleteApplicationInteractor.class));
        deleteApplicationController.deleteApplication(senderUserId, projectId);

        // Verify the application was deleted successfully
        // This would typically involve checking some state or output
    }

    /**
     * Tests that the DeleteApplicationOutputData has the correct sender user ID.
     */
    @Test
    void deleteApplicationOutputDataHasCorrectSenderUserId() {
        int senderUserId = 1;
        int projectId = 1;
        DeleteApplicationOutputData outputData = new DeleteApplicationOutputData(senderUserId, projectId);
        assertEquals(senderUserId, outputData.getSenderUserId());
    }

    /**
     * Tests that the DeleteApplicationOutputData has the correct project ID.
     */
    @Test
    void deleteApplicationOutputDataHasCorrectProjectId() {
        int senderUserId = 1;
        int projectId = 1;
        DeleteApplicationOutputData outputData = new DeleteApplicationOutputData(senderUserId, projectId);
        assertEquals(projectId, outputData.getProjectId());
    }

    /**
     * Tests that the DeleteApplicationOutputData handles a negative sender user ID.
     */
    @Test
    void deleteApplicationOutputDataHandlesNegativeSenderUserId() {
        int senderUserId = -1;
        int projectId = 1;
        DeleteApplicationOutputData outputData = new DeleteApplicationOutputData(senderUserId, projectId);
        assertEquals(senderUserId, outputData.getSenderUserId());
    }

    /**
     * Tests that the DeleteApplicationOutputData handles a negative project ID.
     */
    @Test
    void deleteApplicationOutputDataHandlesNegativeProjectId() {
        int senderUserId = 1;
        int projectId = -1;
        DeleteApplicationOutputData outputData = new DeleteApplicationOutputData(senderUserId, projectId);
        assertEquals(projectId, outputData.getProjectId());
    }

    /**
     * Tests that the DeleteApplicationOutputData handles a zero sender user ID.
     */
    @Test
    void deleteApplicationOutputDataHandlesZeroSenderUserId() {
        int senderUserId = 0;
        int projectId = 1;
        DeleteApplicationOutputData outputData = new DeleteApplicationOutputData(senderUserId, projectId);
        assertEquals(senderUserId, outputData.getSenderUserId());
    }

    /**
     * Tests that the DeleteApplicationOutputData handles a zero project ID.
     */
    @Test
    void deleteApplicationOutputDataHandlesZeroProjectId() {
        int senderUserId = 1;
        int projectId = 0;
        DeleteApplicationOutputData outputData = new DeleteApplicationOutputData(senderUserId, projectId);
        assertEquals(projectId, outputData.getProjectId());
    }
}