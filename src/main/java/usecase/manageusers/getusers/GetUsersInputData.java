package usecase.manageusers.getusers;

/**
 * This is an immutable data structure responsible for storing the input
 * data related to retrieving users.
 */
public class GetUsersInputData {
    /**
     * The ID of the user requesting the projects.
     */
    private final int projectId;

    /**
     * Constructs a GetUsersInputData object.
     *
     * @param projectId the ID of the project requesting the uesrs.
     */
    public GetUsersInputData(int projectId) {
        this.projectId = projectId;
    }

    /**
     * Returns the ID of the project requesting the users.
     *
     * @return the project ID.
     */
    public int getProjectId() {
        return projectId;
    }
}
