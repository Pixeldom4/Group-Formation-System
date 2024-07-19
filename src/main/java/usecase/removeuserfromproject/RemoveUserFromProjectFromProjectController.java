package usecase.removeuserfromproject;


public class RemoveUserFromProjectFromProjectController {
    private final RemoveUserFromProjectInputBoundary removeUserInteractor;

    public RemoveUserFromProjectFromProjectController(RemoveUserFromProjectInputBoundary removeUserInteractor){
        this.removeUserInteractor = removeUserInteractor;
    }

    /**
     * Calls the interactor to remove a user from a project.
     *
     * @param projectId The ID of the project.
     * @param userId The ID of the user.
     */
    public void removeUserFromProject(int projectId, int userId) {
        RemoveUserFromProjectInputData inputData = new RemoveUserFromProjectInputData(projectId, userId);
        removeUserInteractor.removeUserFromProject(inputData);
    }
}
