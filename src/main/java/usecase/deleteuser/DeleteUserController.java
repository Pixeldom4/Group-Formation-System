package usecase.deleteuser;

/**
 * Controller class for deleting users.
 * Interacts with the input boundary to process user deletion.
 */
public class DeleteUserController {
    private final DeleteUserInputBoundary deleteUserInteractor;

    /**
     * Constructs a DeleteUserController object with the provided interactor.
     *
     * @param deleteUserInteractor the delete user interactor.
     */
    public DeleteUserController(DeleteUserInputBoundary deleteUserInteractor) {
        this.deleteUserInteractor = deleteUserInteractor;
    }

    /**
     * Deletes the user with the specific user ID.
     *
     * @param userId the ID of the user.
     */
    public void deleteUser(int userId) {
        DeleteUserInputData inputData = new DeleteUserInputData(userId);
        deleteUserInteractor.deleteUser(inputData);
    }
}
