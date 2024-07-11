package usecase.createuser;

/**
 * The output boundary for creating a user use case.
 */
public interface CreateUserOutputBoundary {
    /**
     * Prepares the success view with the provided output data.
     *
     * @param outputData the output data to present in case of success.
     */
    void prepareSuccessView(CreateUserOutputData outputData);

    /**
     * Prepares the failure view with the provided error message.
     *
     * @param error the error message to present in case of failure.
     */
    void prepareFailView(String error);
}
