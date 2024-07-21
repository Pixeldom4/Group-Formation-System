package usecase.acceptapplication;

import viewmodel.DisplayProjectApplicationViewModel;

/**
 * Presenter class for accepting applications.
 * Implements the output boundary to prepare views for application acceptance.
 */
public class AcceptApplicationPresenter implements AcceptApplicationOutputBoundary {
    private final DisplayProjectApplicationViewModel applicationViewModel;

    /**
     * Constructs an AcceptApplicationPresenter object with the specified view model.
     *
     * @param applicationViewModel the view model to update with acceptance results.
     */
    public AcceptApplicationPresenter(DisplayProjectApplicationViewModel applicationViewModel){
        this.applicationViewModel = applicationViewModel;
    }

    /**
     * Prepares the success view with the provided output data.
     *
     * @param outputData the output data containing the accepted applicant's name.
     */
    @Override
    public void prepareSuccessView(AcceptApplicationOutputData outputData){
        applicationViewModel.setSenderName(outputData.getAcceptedName());
        applicationViewModel.acceptedResult(true);
    }

    /**
     * Prepares the failure view with the provided error message.
     *
     * @param errorMessage the error message to present in case of failure.
     */
    @Override
    public void prepareFailView(String errorMessage) {
        applicationViewModel.setErrorMessage(errorMessage);
        applicationViewModel.acceptedResult(false);
    }
}
