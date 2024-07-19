package usecase.deleteuser;

/**
 * This is an immutable data structure responsible for storing the input
 * data related to deleting a user.
 */
public class DeleteUserInputData {
    private final int userId;

    /**
     * Constructs a DeleteUserInputData object with the specified details.
     *
     * @param userId the ID of the user.
     */
    public DeleteUserInputData(int userId){
        this.userId = userId;
    }

    /**
     * Returns the user ID of the user to delete.
     *
     * @return the user ID.
     */
    public int getUserId() {
        return userId;
    }
}
