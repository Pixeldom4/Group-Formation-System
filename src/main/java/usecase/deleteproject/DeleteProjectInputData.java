package usecase.deleteproject;

/**
 * This is an immutable data structure responsible for storing the input
 * data related to deleting a project.
 */
public class DeleteProjectInputData {
    private final int projectId;

    /**
     * Constructs a DeleteProjectInputData object with the specified project ID.
     *
     * @param projectId the ID of the project to be deleted.
     */
    public DeleteProjectInputData(int projectId) {
        this.projectId = projectId;
    }

    /**
     * Gets the project ID.
     *
     * @return the project ID.
     */
    public int getProjectId() {
        return projectId;
    }
}
