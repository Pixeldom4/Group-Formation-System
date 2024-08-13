package usecase.manageapplications.acceptapplication;

/**
 * This is an immutable data structure responsible for storing the input
 * data related to accepting an application.
 */
public class AcceptApplicationInputData {
    private final int userId;
    private final int projectId;

    /**
     * Constructs an AcceptApplicationInputData object with the specified project ID and user ID.
     *
     * @param projectId the ID of the project.
     * @param userId the ID of the user.
     */
    public AcceptApplicationInputData(int projectId, int userId){
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
