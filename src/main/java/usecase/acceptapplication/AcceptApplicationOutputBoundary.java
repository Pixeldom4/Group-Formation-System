package usecase.acceptapplication;

public interface AcceptApplicationOutputBoundary {
    void prepareSuccessView(AcceptApplicationOutputData outputData);

    /**
     * Prepares the failure view with the provided error message.
     *
     * @param errorMessage the error message to present in case of failure.
     */
    void prepareFailView(String errorMessage);
}
