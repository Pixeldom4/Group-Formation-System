package usecase.manageapplications;

import config.DataAccessConfig;
import dataaccess.IApplicationRepository;
import dataaccess.IUserProjectsRepository;
import dataaccess.IUserRepository;
import usecase.manageapplications.acceptapplication.AcceptApplicationInputBoundary;
import usecase.manageapplications.acceptapplication.AcceptApplicationInteractor;
import usecase.manageapplications.acceptapplication.AcceptApplicationOutputBoundary;
import usecase.manageapplications.acceptapplication.AcceptApplicationPresenter;
import usecase.manageapplications.getapplications.GetApplicationsInputBoundary;
import usecase.manageapplications.getapplications.GetApplicationsInteractor;
import usecase.manageapplications.getapplications.GetApplicationsOutputBoundary;
import usecase.manageapplications.getapplications.GetApplicationsPresenter;
import usecase.manageapplications.rejectapplication.RejectApplicationInputBoundary;
import usecase.manageapplications.rejectapplication.RejectApplicationInteractor;
import usecase.manageapplications.rejectapplication.RejectApplicationOutputBoundary;
import usecase.manageapplications.rejectapplication.RejectApplicationPresenter;
import viewmodel.DisplayProjectApplicationViewModel;

public class ManageApplicationsUseCaseFactory {
    private static final IApplicationRepository applicationRepository = DataAccessConfig.getApplicationRepository();
    private static final IUserRepository userRepository = DataAccessConfig.getUserRepository();
    private static final IUserProjectsRepository userProjectsRepository = DataAccessConfig.getUserProjectsRepository();


    // Private constructor to prevent instantiation
    private ManageApplicationsUseCaseFactory() {}

    public static ManageApplicationsController createController(DisplayProjectApplicationViewModel displayProjectApplicationViewModel) {
        GetApplicationsOutputBoundary getApplicationsPresenter = new GetApplicationsPresenter(displayProjectApplicationViewModel);
        GetApplicationsInputBoundary getApplicationsInteractor = new GetApplicationsInteractor(getApplicationsPresenter, applicationRepository, userRepository);

        AcceptApplicationOutputBoundary acceptApplicationPresenter = new AcceptApplicationPresenter(displayProjectApplicationViewModel);
        AcceptApplicationInputBoundary acceptApplicationsInteractor = new AcceptApplicationInteractor(acceptApplicationPresenter, applicationRepository,
                                                                                                      userProjectsRepository, userRepository);

        RejectApplicationOutputBoundary rejectApplicationPresenter = new RejectApplicationPresenter(displayProjectApplicationViewModel);
        RejectApplicationInputBoundary rejectApplicationsInteractor = new RejectApplicationInteractor(rejectApplicationPresenter, applicationRepository, userRepository);

        return new ManageApplicationsController(getApplicationsInteractor, acceptApplicationsInteractor, rejectApplicationsInteractor);
    }
}
