package usecase.deleteapplication;

/**
 * Represents the output data after deleting an application.
 */
public class DeleteApplicationOutputData {
    private final int senderUserId;
    private final int projectId;

    /**
     * Constructs a new DeleteApplicationOutputData instance.
     *
     * @param senderUserId the ID of the user who sent the delete request
     * @param projectId the ID of the project associated with the deleted application
     */
    public DeleteApplicationOutputData(int senderUserId, int projectId) {
        this.senderUserId = senderUserId;
        this.projectId = projectId;
    }

    /**
     * Gets the ID of the user who sent the delete request.
     *
     * @return the sender user ID
     */
    public int getSenderUserId() {
        return senderUserId;
    }

    /**
     * Gets the ID of the project associated with the deleted application.
     *
     * @return the project ID
     */
    public int getProjectId() {
        return projectId;
    }
}