package usecase.manageapplications.deleteapplication;

/**
 * Represents the input data required to delete an application.
 */
public class DeleteApplicationInputData {
    private final int senderUserId;
    private final int projectId;

    /**
     * Constructs a new DeleteApplicationInputData instance.
     *
     * @param senderUserId the ID of the user sending the delete request
     * @param projectId the ID of the project associated with the application to be deleted
     */
    public DeleteApplicationInputData(int senderUserId, int projectId) {
        this.senderUserId = senderUserId;
        this.projectId = projectId;
    }

    /**
     * Gets the ID of the user sending the delete request.
     *
     * @return the sender user ID
     */
    public int getSenderUserId() {
        return senderUserId;
    }

    /**
     * Gets the ID of the project associated with the application to be deleted.
     *
     * @return the project ID
     */
    public int getProjectId() {
        return projectId;
    }
}