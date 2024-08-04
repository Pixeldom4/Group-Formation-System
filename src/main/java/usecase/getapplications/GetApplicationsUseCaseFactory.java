package usecase.getapplications;

import dataaccess.DataAccessConfig;
import dataaccess.IApplicationRepository;
import dataaccess.IUserRepository;
import viewmodel.DisplayProjectApplicationViewModel;

/**
 * Factory class for creating instances of the GetApplications use case.
 */
public class GetApplicationsUseCaseFactory {
    private static final IApplicationRepository applicationRepository = DataAccessConfig.getApplicationRepository();
    private static final IUserRepository userRepository = DataAccessConfig.getUserRepository();

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
        GetApplicationsInputBoundary interactor = new GetApplicationsInteractor(presenter, applicationRepository, userRepository);
        return new GetApplicationsController(interactor);
    }
}
