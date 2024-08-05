package usecase.manageusers.edituser;

/**
 * Interface for the Edit User output boundary.
 * Defines methods to prepare views for success and failure scenarios.
 */
public interface EditUserOutputBoundary {
    /**
     * Prepares the success view with the provided output data.
     *
     * @param outputData the output data to present in case of success.
     */
    void prepareSuccessView(EditUserOutputData outputData);

    /**
     * Prepares the failure view with the provided error message.
     *
     * @param error the error message to present in case of failure.
     */
    void prepareFailView(String error);
}
