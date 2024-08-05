package usecase.manageprojects.getprojects;

/**
 * This is an immutable data structure responsible for storing the input
 * data related to retrieving projects.
 */
public class GetProjectsInputData {
    /**
     * The ID of the user requesting the projects.
     */
    private final int userId;

    /**
     * Constructs a GetProjectsInputData object.
     *
     * @param userId the ID of the user requesting the projects
     */
    public GetProjectsInputData(int userId) {
        this.userId = userId;
    }

    /**
     * Returns the ID of the user requesting the projects.
     *
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }
}
