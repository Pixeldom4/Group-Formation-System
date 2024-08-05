package usecase.manageapplications.rejectapplication;

import viewmodel.DisplayProjectApplicationViewModel;

/**
 * Presenter class for rejecting applications.
 * Implements the output boundary to prepare views for application rejection.
 */
public class RejectApplicationPresenter implements RejectApplicationOutputBoundary {
    private final DisplayProjectApplicationViewModel applicationViewModel;

    /**
     * Constructs a RejectApplicationPresenter with the specified view model.
     *
     * @param applicationViewModel the view model to update with rejection results.
     */
    public RejectApplicationPresenter(DisplayProjectApplicationViewModel applicationViewModel) {
        this.applicationViewModel = applicationViewModel;
    }

    /**
     * Prepares the success view with the provided output data.
     *
     * @param outputData the output data indicating success.
     */
    @Override
    public void prepareSuccessView(RejectApplicationOutputData outputData) {
        applicationViewModel.setSenderName(outputData.getRejectedName());
        applicationViewModel.rejectedResult(true);
    }

    /**
     * Prepares the failure view with the provided error message.
     *
     * @param errorMessage the error message to present in case of failure.
     */
    @Override
    public void prepareFailView(String errorMessage) {
        applicationViewModel.setErrorMessage(errorMessage);
        applicationViewModel.rejectedResult(false);
    }
}
