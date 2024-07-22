package usecase.getprojects;

/**
 * This is an immutable data structure responsible for storing the input
 * data related to retrieving projects.
 */
public class GetProjectsInputData {
    /**
     * Constructs a GetProjectsInputData object.
     */
    private final int userId;
    public GetProjectsInputData(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }
}
