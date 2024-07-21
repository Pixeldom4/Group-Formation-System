package usecase.getapplications;

/**
 * This is an immutable data structure responsible for storing the input
 * data related to retrieving applications.
 */
public class GetApplicationsInputData {
    private final int userId;
    private final int projectId;

    /**
     * Constructs a GetApplicationsInputData object with the specified project ID and user ID.
     *
     * @param projectId the ID of the project.
     * @param userId the ID of the user.
     */
    public GetApplicationsInputData(int projectId, int userId) {
        this.projectId = projectId;
        this.userId = userId;
    }

    /**
     * Constructs a GetApplicationsInputData object with the specified project ID.
     *
     * @param projectId the ID of the project.
     */
    public GetApplicationsInputData(int projectId) {
        this.projectId = projectId;
        this.userId = -1; // Default value indicating no user ID provided
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
