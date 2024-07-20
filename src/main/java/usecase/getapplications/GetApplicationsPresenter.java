package usecase.getapplications;

import viewmodel.DisplayProjectApplicationViewModel;

public class GetApplicationsPresenter implements GetApplicationsOutputBoundary {
    private final DisplayProjectApplicationViewModel applicationViewModel;

    public GetApplicationsPresenter(DisplayProjectApplicationViewModel applicationViewModel) {
        this.applicationViewModel = applicationViewModel;
    }

    @Override
    public void prepareSuccessView(GetApplicationsOutputData outputData){
        applicationViewModel.setApplicationData(outputData.getApplications());
        applicationViewModel.applicationResult(true);
    }

    @Override
    public void prepareFailView(String errorMessage) {
        applicationViewModel.setErrorMessage(errorMessage);
        applicationViewModel.applicationResult(false);
    }
}
