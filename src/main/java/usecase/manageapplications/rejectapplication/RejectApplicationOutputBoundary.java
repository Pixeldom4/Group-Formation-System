package usecase.manageapplications.rejectapplication;

/**
 * Output boundary interface for rejecting applications.
 * Defines methods to prepare success and failure views.
 */
public interface RejectApplicationOutputBoundary {
    /**
     * Prepares the success view with the provided output data.
     *
     * @param outputData the output data to present in case of success.
     */
    void prepareSuccessView(RejectApplicationOutputData outputData);

    /**
     * Prepares the failure view with the provided error message.
     *
     * @param errorMessage the error message to present in case of failure.
     */
    void prepareFailView(String errorMessage);
}
