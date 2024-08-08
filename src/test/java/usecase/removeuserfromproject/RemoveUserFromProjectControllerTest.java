package usecase.removeuserfromproject;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for the RemoveUserFromProjectController class.
 */
@SuppressWarnings("FieldCanBeLocal")
public class RemoveUserFromProjectControllerTest {
    private RemoveUserFromProjectInputBoundary interactor;
    private RemoveUserFromProjectFromProjectController controller;

    @Test
    void testCallInteractor() {
        int projectId = 1;
        int userId = 1;
        int editorId = 1;

        RemoveUserFromProjectInputData inputData = new RemoveUserFromProjectInputData(projectId, userId, editorId);
        interactor = mock(RemoveUserFromProjectInputBoundary.class);
        controller = new RemoveUserFromProjectFromProjectController(interactor);

        controller.removeUserFromProject(projectId, userId, editorId);

        verify(interactor).removeUserFromProject(inputData);
    }

}
