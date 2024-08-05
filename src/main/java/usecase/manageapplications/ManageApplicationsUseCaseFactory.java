package usecase.manageapplications;

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



    // Private constructor to prevent instantiation
    private ManageApplicationsUseCaseFactory() {}

    public static ManageApplicationsController createController(DisplayProjectApplicationViewModel displayProjectApplicationViewModel) {
        GetApplicationsOutputBoundary getApplicationsPresenter = new GetApplicationsPresenter(displayProjectApplicationViewModel);
        GetApplicationsInputBoundary getApplicationsInteractor = new GetApplicationsInteractor(getApplicationsPresenter);

        AcceptApplicationOutputBoundary acceptApplicationPresenter = new AcceptApplicationPresenter(displayProjectApplicationViewModel);
        AcceptApplicationInputBoundary acceptApplicationsInteractor = new AcceptApplicationInteractor(acceptApplicationPresenter);

        RejectApplicationOutputBoundary rejectApplicationPresenter = new RejectApplicationPresenter(displayProjectApplicationViewModel);
        RejectApplicationInputBoundary rejectApplicationsInteractor = new RejectApplicationInteractor(rejectApplicationPresenter);

        return new ManageApplicationsController(getApplicationsInteractor, acceptApplicationsInteractor, rejectApplicationsInteractor);
    }
}
