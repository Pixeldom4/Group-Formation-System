package usecase.rejectapplication;

import view.DisplayProjectApplicationView;

public class RejectApplicationPresenter implements RejectApplicationOutputBoundary {
    private final DisplayProjectApplicationView applicationView;

    public RejectApplicationPresenter(DisplayProjectApplicationView applicationView){
        this.applicationView = applicationView;
    }

    @Override
    public void prepareSuccessView(RejectApplicationOutputData outputData){

    }

    @Override
    public void prepareFailView(String errorMessage) {

    }
}
