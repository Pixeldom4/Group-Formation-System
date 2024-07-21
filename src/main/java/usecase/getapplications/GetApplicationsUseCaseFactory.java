package usecase.getapplications;

import viewmodel.DisplayProjectApplicationViewModel;

/**
 * Factory class for creating instances of the GetApplications use case.
 */
public class GetApplicationsUseCaseFactory {

    // Private constructor to prevent instantiation
    private GetApplicationsUseCaseFactory() {
    }

    /**
     * Creates a new GetApplicationsController with the given DisplayProjectApplicationViewModel.
     *
     * @param displayProjectApplicationViewModel the DisplayProjectApplicationViewModel
     * @return a new GetApplicationsController
     */
    public static GetApplicationsController createController(DisplayProjectApplicationViewModel displayProjectApplicationViewModel) {
        GetApplicationsOutputBoundary presenter = new GetApplicationsPresenter(displayProjectApplicationViewModel);
        GetApplicationsInputBoundary interactor = new GetApplicationsInteractor(presenter);
        return new GetApplicationsController(interactor);
    }
}
