package usecase.acceptapplication;

import viewmodel.DisplayProjectApplicationViewModel;

public class AcceptApplicationUseCaseFactory {

    private AcceptApplicationUseCaseFactory() {}

    public static AcceptApplicationController createController(DisplayProjectApplicationViewModel displayProjectApplicationViewModel) {
        AcceptApplicationOutputBoundary presenter = new AcceptApplicationPresenter(displayProjectApplicationViewModel);
        AcceptApplicationInputBoundary interactor = new AcceptApplicationInteractor(presenter);
        return new AcceptApplicationController(interactor);
    }
}
