package usecase.manageapplications;

public interface ManageApplicationsOutputBoundary {
    void prepareSuccessProjectApplicationView(ManageApplicationsOutputData outputData);
    void prepareSuccessSelfApplicationView(ManageApplicationsOutputData outputData);
    void prepareSuccessDecisionView(String decisionText);

    /**
     * Prepares the failure view with the provided error message.
     *
     * @param errorMessage the error message to present in case of failure.
     */
    void prepareFailView(String errorMessage);
}
