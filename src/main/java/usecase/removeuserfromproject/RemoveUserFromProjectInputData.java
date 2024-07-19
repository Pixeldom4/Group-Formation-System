package usecase.removeuserfromproject;

/**
 * This is an immutable data structure responsible for storing the input
 * data related to removing a user from a project.
 */
public class RemoveUserFromProjectInputData {
    private final int projectId;
    private final int userId;
    private final int editorId;

    /**
     * Constructs a RemoveUserFromProjectInputData object with the given details.
     *
     * @param projectId the ID of the project to remove the user from.
     * @param userId the ID of the user to remove from the project.
     * @param editorId the ID of the user trying to remove a user from the project.
     */
    public RemoveUserFromProjectInputData(int projectId, int userId, int editorId) {
        this.projectId = projectId;
        this.userId = userId;
        this.editorId = editorId;
    }

    /**
     * Returns the project ID.
     *
     * @return the project ID.
     */
    public int getProjectId() {
        return projectId;
    }

    /**
     * Returns the user ID of the user to remove.
     *
     * @return the user ID of the user to remove.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Returns the user ID of the user requesting to remove the user from the project.
     *
     * @return the user ID of the user requesting to remove the user from the project.
     */
    public int getEditorId() {
        return editorId;
    }
}
