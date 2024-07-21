package usecase.acceptapplication;

/**
 * Output boundary interface for accepting applications.
 * Defines methods to prepare success and failure views.
 */
public interface AcceptApplicationOutputBoundary {
    /**
     * Prepares the success view with the provided output data.
     *
     * @param outputData the output data containing the accepted applicant's name.
     */
    void prepareSuccessView(AcceptApplicationOutputData outputData);

    /**
     * Prepares the failure view with the provided error message.
     *
     * @param errorMessage the error message to present in case of failure.
     */
    void prepareFailView(String errorMessage);
}
