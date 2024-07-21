package usecase.deleteapplication;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Interactor class responsible for handling the deletion of applications.
 */
class DeleteApplicationInteractor {
    /**
     * Deletes an application based on the provided input data.
     *
     * @param inputData the input data containing the sender user ID and project ID
     */
    void deleteApplication(DeleteApplicationInputData inputData) {
        // Implementation here
    }
}

/**
 * Controller class for handling delete application requests.
 */
class DeleteApplicationController {
    private final DeleteApplicationInteractor interactor;

    /**
     * Constructs a DeleteApplicationController with the specified interactor.
     *
     * @param interactor the interactor to handle the deletion logic
     */
    DeleteApplicationController(DeleteApplicationInteractor interactor) {
        this.interactor = interactor;
    }

    /**
     * Deletes an application based on the provided sender user ID and project ID.
     *
     * @param senderUserId the ID of the user sending the request
     * @param projectId the ID of the project to be deleted
     * @throws IllegalArgumentException if the sender user ID or project ID is invalid
     */
    void deleteApplication(int senderUserId, int projectId) {
        if (senderUserId <= 0 || projectId <= 0) {
            throw new IllegalArgumentException("Invalid IDs");
        }
        DeleteApplicationInputData inputData = new DeleteApplicationInputData(senderUserId, projectId);
        interactor.deleteApplication(inputData);
    }
}

/**
 * Data class representing the input data for deleting an application.
 */
class DeleteApplicationInputData {
    private final int senderUserId;
    private final int projectId;

    /**
     * Constructs a DeleteApplicationInputData with the specified sender user ID and project ID.
     *
     * @param senderUserId the ID of the user sending the request
     * @param projectId the ID of the project to be deleted
     */
    DeleteApplicationInputData(int senderUserId, int projectId) {
        this.senderUserId = senderUserId;
        this.projectId = projectId;
    }

    // Getters here
}

/**
 * Test class for DeleteApplicationController.
 */
class DeleteApplicationControllerTest {

    private final DeleteApplicationInteractor deleteApplicationInteractor = new DeleteApplicationInteractor();
    private final DeleteApplicationController deleteApplicationController = new DeleteApplicationController(deleteApplicationInteractor);

    @Test
    void deleteApplicationSuccessfully() {
        int senderUserId = 1;
        int projectId = 1;
        DeleteApplicationInputData inputData = new DeleteApplicationInputData(senderUserId, projectId);

        deleteApplicationController.deleteApplication(senderUserId, projectId);

        // Verify the application was deleted successfully
        // This would typically involve checking some state or output
    }

    @Test
    void deleteApplicationWithInvalidUserIdThrowsException() {
        int invalidUserId = -1;
        int projectId = 1;

        assertThrows(IllegalArgumentException.class, () -> deleteApplicationController.deleteApplication(invalidUserId, projectId));
    }

    @Test
    void deleteApplicationWithInvalidProjectIdThrowsException() {
        int senderUserId = 1;
        int invalidProjectId = -1;

        assertThrows(IllegalArgumentException.class, () -> deleteApplicationController.deleteApplication(senderUserId, invalidProjectId));
    }

    @Test
    void deleteApplicationWithBothIdsInvalidThrowsException() {
        int invalidUserId = -1;
        int invalidProjectId = -1;

        assertThrows(IllegalArgumentException.class, () -> deleteApplicationController.deleteApplication(invalidUserId, invalidProjectId));
    }
}
