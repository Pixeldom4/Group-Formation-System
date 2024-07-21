package usecase.deleteapplication;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DeleteApplicationInteractor {
    void deleteApplication(DeleteApplicationInputData inputData) {
        // Implementation here
    }
}

class DeleteApplicationController {
    private final DeleteApplicationInteractor interactor;

    DeleteApplicationController(DeleteApplicationInteractor interactor) {
        this.interactor = interactor;
    }

    void deleteApplication(int senderUserId, int projectId) {
        if (senderUserId <= 0 || projectId <= 0) {
            throw new IllegalArgumentException("Invalid IDs");
        }
        DeleteApplicationInputData inputData = new DeleteApplicationInputData(senderUserId, projectId);
        interactor.deleteApplication(inputData);
    }
}

class DeleteApplicationInputData {
    private final int senderUserId;
    private final int projectId;

    DeleteApplicationInputData(int senderUserId, int projectId) {
        this.senderUserId = senderUserId;
        this.projectId = projectId;
    }

    // Getters here
}

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