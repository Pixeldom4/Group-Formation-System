package usecase.manageapplications.rejectapplication;

import config.DataAccessConfig;
import dataaccess.IApplicationRepository;
import dataaccess.IUserRepository;
import viewmodel.DisplayProjectApplicationViewModel;

/**
 * Factory class for creating instances of the RejectApplication use case.
 */
public class RejectApplicationUseCaseFactory {
    private final static IApplicationRepository applicationRepository = DataAccessConfig.getApplicationRepository();
    private final static IUserRepository userRepository = DataAccessConfig.getUserRepository();

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
        RejectApplicationInputBoundary interactor = new RejectApplicationInteractor(presenter, applicationRepository, userRepository);
        return new RejectApplicationController(interactor);
    }
}
