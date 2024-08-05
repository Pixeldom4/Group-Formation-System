package usecase.manageprojects.deleteproject;

/**
 * Controller class for deleting projects.
 * Interacts with the input boundary to process project deletion.
 */
public class DeleteProjectController {
    private final DeleteProjectInputBoundary deleteProjectInteractor;

    /**
     * Constructs a DeleteProjectController.
     *
     * @param deleteProjectInteractor the interactor that handles the delete project use case
     */
    public DeleteProjectController(DeleteProjectInputBoundary deleteProjectInteractor) {
        this.deleteProjectInteractor = deleteProjectInteractor;
    }

    /**
     * Calls the interactor to delete a project.
     *
     * @param projectId the ID of the project to be deleted.
     */
    public void deleteProject(int projectId) {
        DeleteProjectInputData inputData = new DeleteProjectInputData(projectId);
        deleteProjectInteractor.deleteProject(inputData);
    }
}
