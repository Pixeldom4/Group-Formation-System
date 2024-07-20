package usecase.acceptapplication;

import view.DisplayProjectApplicationView;

public class AcceptApplicationPresenter implements AcceptApplicationOutputBoundary {
    private final DisplayProjectApplicationView applicationView;

    public AcceptApplicationPresenter(DisplayProjectApplicationView applicationView){
        this.applicationView = applicationView;
    }

    @Override
    public void prepareSuccessView(AcceptApplicationOutputData outputData){

    }

    @Override
    public void prepareFailView(String errorMessage) {

    }
}
