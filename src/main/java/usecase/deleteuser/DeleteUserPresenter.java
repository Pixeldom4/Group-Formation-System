package usecase.deleteuser;

/**
 * Presenter for the Delete User use case.
 * Implements the DeleteUserOutputBoundary interface to handle the output data.
 */
public class DeleteUserPresenter implements DeleteUserOutputBoundary {

    /**
     * Prepares the success view with the given output data.
     *
     * @param outputData The output data to prepare the success view with.
     */
    @Override
    public void prepareSuccessView(DeleteUserOutputData outputData) {
        // Implementation here
    }

    /**
     * Prepares the fail view with the given error message.
     *
     * @param error The error message to prepare the fail view with.
     */
    @Override
    public void prepareFailView(String error) {
        // Implementation here
    }
}
