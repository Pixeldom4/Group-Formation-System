package usecase.deleteuser;

/**
 * The input boundary for creating a user use case.
 */
public interface DeleteUserInputBoundary {
    /**
     * Deletes a user with the provided input data.
     *
     * @param inputData the input data required to delete the user.
     */
     void deleteUser(DeleteUserInputData inputData);
}
