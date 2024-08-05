package usecase.manageprojects.deleteproject;

/**
 * This is an immutable data structure responsible for storing the output
 * data related to deleting a project.
 */
public class DeleteProjectOutputData {
    private final int projectId;

    /**
     * Constructs a DeleteProjectOutputData object with the specified project ID.
     *
     * @param projectId the ID of the deleted project.
     */
    public DeleteProjectOutputData(int projectId) {
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
