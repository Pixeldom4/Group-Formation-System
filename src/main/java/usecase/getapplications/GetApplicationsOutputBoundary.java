package usecase.getapplications;

public interface GetApplicationsOutputBoundary {
    void prepareSuccessView(GetApplicationsOutputData outputData);

    /**
     * Prepares the failure view with the provided error message.
     *
     * @param errorMessage the error message to present in case of failure.
     */
    void prepareFailView(String errorMessage);
}
