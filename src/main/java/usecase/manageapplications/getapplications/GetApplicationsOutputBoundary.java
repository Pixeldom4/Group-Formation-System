package usecase.manageapplications.getapplications;

/**
 * Output boundary interface for retrieving applications.
 * Defines methods to prepare success and failure views.
 */
public interface GetApplicationsOutputBoundary {
    /**
     * Prepares the success view with the provided output data.
     *
     * @param outputData the output data to present in case of success.
     */
    void prepareSuccessView(GetApplicationsOutputData outputData);

    /**
     * Prepares the failure view with the provided error message.
     *
     * @param errorMessage the error message to present in case of failure.
     */
    void prepareFailView(String errorMessage);
}
