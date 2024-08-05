package usecase.manageapplications.acceptapplication;

import viewmodel.DisplayProjectApplicationViewModel;

/**
 * Factory class for creating instances of the AcceptApplication use case.
 */
public class AcceptApplicationUseCaseFactory {

    // Private constructor to prevent instantiation
    private AcceptApplicationUseCaseFactory() {}

    /**
     * Creates and returns an AcceptApplicationController instance.
     *
     * @param displayProjectApplicationViewModel the view model to update with acceptance results.
     * @return an AcceptApplicationController instance.
     */
    public static AcceptApplicationController createController(DisplayProjectApplicationViewModel displayProjectApplicationViewModel) {
        AcceptApplicationOutputBoundary presenter = new AcceptApplicationPresenter(displayProjectApplicationViewModel);
        AcceptApplicationInputBoundary interactor = new AcceptApplicationInteractor(presenter);
        return new AcceptApplicationController(interactor);
    }
}
