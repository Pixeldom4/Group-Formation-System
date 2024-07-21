package usecase.acceptapplication;

import viewmodel.DisplayProjectApplicationViewModel;

public class AcceptApplicationPresenter implements AcceptApplicationOutputBoundary {
    private final DisplayProjectApplicationViewModel applicationViewModel;

    public AcceptApplicationPresenter(DisplayProjectApplicationViewModel applicationViewModel){
        this.applicationViewModel = applicationViewModel;
    }

    @Override
    public void prepareSuccessView(AcceptApplicationOutputData outputData){
        applicationViewModel.setSenderName(outputData.getAcceptedName());
        applicationViewModel.acceptedResult(true);
    }

    @Override
    public void prepareFailView(String errorMessage) {
        applicationViewModel.setErrorMessage(errorMessage);
        applicationViewModel.acceptedResult(false);
    }
}
