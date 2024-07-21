package usecase.removeuserfromproject;

/**
 * Controller class for removing a user from a project.
 * Interacts with the input boundary to process user removal from a project.
 */
public class RemoveUserFromProjectFromProjectController {
    private final RemoveUserFromProjectInputBoundary removeUserInteractor;

    /**
     * Constructs a RemoveUserFromProjectFromProjectController.
     *
     * @param removeUserInteractor the interactor that handles the remove user from project use case.
     */
    public RemoveUserFromProjectFromProjectController(RemoveUserFromProjectInputBoundary removeUserInteractor) {
        this.removeUserInteractor = removeUserInteractor;
    }

    /**
     * Calls the interactor to remove a user from a project.
     *
     * @param projectId the ID of the project.
     * @param userId    the ID of the user.
     * @param editorId  the ID of the user requesting to remove a user from the project.
     */
    public void removeUserFromProject(int projectId, int userId, int editorId) {
        RemoveUserFromProjectInputData inputData = new RemoveUserFromProjectInputData(projectId, userId, editorId);
        removeUserInteractor.removeUserFromProject(inputData);
    }
}
