package usecase.getapplications;

import viewmodel.DisplayProjectApplicationViewModel;

/**
 * Presenter class for retrieving applications.
 * Implements the output boundary to prepare views for application retrieval.
 */
public class GetApplicationsPresenter implements GetApplicationsOutputBoundary {
    private final DisplayProjectApplicationViewModel applicationViewModel;

    /**
     * Constructs a GetApplicationsPresenter with the specified view model.
     *
     * @param applicationViewModel the view model to update with retrieval results.
     */
    public GetApplicationsPresenter(DisplayProjectApplicationViewModel applicationViewModel) {
        this.applicationViewModel = applicationViewModel;
    }

    /**
     * Prepares the success view with the provided output data.
     *
     * @param outputData the output data indicating success.
     */
    @Override
    public void prepareSuccessView(GetApplicationsOutputData outputData) {
        applicationViewModel.setApplicationData(outputData.getApplications());
        applicationViewModel.applicationResult(true);
    }

    /**
     * Prepares the failure view with the provided error message.
     *
     * @param errorMessage the error message to present in case of failure.
     */
    @Override
    public void prepareFailView(String errorMessage) {
        applicationViewModel.setErrorMessage(errorMessage);
        applicationViewModel.applicationResult(false);
    }
}
