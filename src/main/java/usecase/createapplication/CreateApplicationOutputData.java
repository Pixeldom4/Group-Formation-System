package usecase.createapplication;

/**
 * This is an immutable data structure responsible for storing the output
 * data related to creating an application.
 */
public class CreateApplicationOutputData {
    private final int senderUserId;
    private final int projectId;
    private final boolean success;

    /**
     * Constructs a CreateApplicationOutputData object with the specified details.
     *
     * @param senderUserId the ID of the user sending the application.
     * @param projectId    the ID of the project for which the application is created.
     * @param success      whether the application creation was successful.
     */
    public CreateApplicationOutputData(int senderUserId, int projectId, boolean success) {
        this.senderUserId = senderUserId;
        this.projectId = projectId;
        this.success = success;
    }

    /**
     * Returns the ID of the user sending the application.
     *
     * @return the sender user ID.
     */
    public int getSenderUserId() {
        return senderUserId;
    }

    /**
     * Returns the ID of the project for which the application was created.
     *
     * @return the project ID.
     */
    public int getProjectId() {
        return projectId;
    }

    /**
     * Returns whether the application creation was successful.
     *
     * @return true if the application creation was successful, otherwise false.
     */
    public boolean isSuccess() {
        return success;
    }
}
