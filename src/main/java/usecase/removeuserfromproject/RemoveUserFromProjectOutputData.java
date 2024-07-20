package usecase.removeuserfromproject;

/**
 * This is an immutable data structure responsible for storing the input
 * data related to removing a user from a project.
 */
public class RemoveUserFromProjectOutputData {
    private final int projectId;
    private final int userId;

    /**
     * Constructs a RemoveUserFromProjectInputData object with the given details.
     *
     * @param projectId the ID of the project to remove the user from.
     * @param userId the ID of the user to remove from the project.
     */
    public RemoveUserFromProjectOutputData(int projectId, int userId) {
        this.projectId = projectId;
        this.userId = userId;
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
     * Returns the user ID.
     *
     * @return the user ID.
     */
    public int getUserId() {
        return userId;
    }
}

