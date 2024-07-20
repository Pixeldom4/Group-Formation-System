package usecase.rejectapplication;

import viewmodel.DisplayProjectApplicationViewModel;

public class RejectApplicationUseCaseFactory {

    private RejectApplicationUseCaseFactory() {}

    public static RejectApplicationController createController(DisplayProjectApplicationViewModel displayProjectApplicationViewModel) {
        RejectApplicationOutputBoundary presenter = new RejectApplicationPresenter(displayProjectApplicationViewModel);
        RejectApplicationInputBoundary interactor = new RejectApplicationInteractor(presenter);
        return new RejectApplicationController(interactor);
    }
}
