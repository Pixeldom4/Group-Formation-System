package usecase.manageapplications.rejectapplication;

/**
 * This is an immutable data structure responsible for storing the input
 * data related to rejecting an application.
 */
public class RejectApplicationInputData {
    private final int userId;
    private final int projectId;

    /**
     * Constructs a RejectApplicationInputData object with the specified project ID and user ID.
     *
     * @param projectId the ID of the project.
     * @param userId the ID of the user to be rejected.
     */
    public RejectApplicationInputData(int projectId, int userId) {
        this.projectId = projectId;
        this.userId = userId;
    }

    /**
     * Gets the user ID.
     *
     * @return the user ID.
     */
    public int getUserId() {
        return userId;
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
