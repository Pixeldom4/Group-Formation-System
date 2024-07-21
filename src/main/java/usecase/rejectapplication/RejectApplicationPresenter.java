package usecase.rejectapplication;

import viewmodel.DisplayProjectApplicationViewModel;

public class RejectApplicationPresenter implements RejectApplicationOutputBoundary {
    private final DisplayProjectApplicationViewModel applicationViewModel;

    public RejectApplicationPresenter(DisplayProjectApplicationViewModel applicationViewModel){
        this.applicationViewModel = applicationViewModel;
    }

    @Override
    public void prepareSuccessView(RejectApplicationOutputData outputData){
        applicationViewModel.setSenderName(outputData.getRejectedName());
        applicationViewModel.rejectedResult(true);
    }

    @Override
    public void prepareFailView(String errorMessage) {
        applicationViewModel.setErrorMessage(errorMessage);
        applicationViewModel.rejectedResult(false);
    }
}
