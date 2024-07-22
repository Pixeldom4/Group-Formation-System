package usecase.removeuserfromproject;

/**
 * Interface for the output boundary of the Remove User From Project use case.
 * Provides methods to prepare views for success and failure scenarios.
 */
public interface RemoveUserFromProjectOutputBoundary {
    /**
     * Prepares the success view with the provided output data.
     *
     * @param outputData the output data to present in case of success.
     */
    void prepareSuccessView(RemoveUserFromProjectOutputData outputData);

    /**
     * Prepares the failure view with the provided error message.
     *
     * @param error the error message to present in case of failure.
     */
    void prepareFailView(String error);
}
