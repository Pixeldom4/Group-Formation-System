package usecase.rejectapplication;

import viewmodel.DisplayProjectApplicationViewModel;

/**
 * Factory class for creating instances of the RejectApplication use case.
 */
public class RejectApplicationUseCaseFactory {

    // Private constructor to prevent instantiation
    private RejectApplicationUseCaseFactory() {}

    /**
     * Creates a new RejectApplicationController with the given DisplayProjectApplicationViewModel.
     *
     * @param displayProjectApplicationViewModel the DisplayProjectApplicationViewModel.
     * @return a new RejectApplicationController.
     */
    public static RejectApplicationController createController(DisplayProjectApplicationViewModel displayProjectApplicationViewModel) {
        RejectApplicationOutputBoundary presenter = new RejectApplicationPresenter(displayProjectApplicationViewModel);
        RejectApplicationInputBoundary interactor = new RejectApplicationInteractor(presenter);
        return new RejectApplicationController(interactor);
    }
}
