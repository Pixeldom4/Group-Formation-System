package usecase.manageapplications;

import usecase.acceptapplication.AcceptApplicationInputBoundary;
import usecase.acceptapplication.AcceptApplicationInteractor;
import usecase.acceptapplication.AcceptApplicationOutputBoundary;
import usecase.acceptapplication.AcceptApplicationPresenter;
import usecase.getapplications.*;
import usecase.rejectapplication.RejectApplicationInputBoundary;
import usecase.rejectapplication.RejectApplicationInteractor;
import usecase.rejectapplication.RejectApplicationOutputBoundary;
import usecase.rejectapplication.RejectApplicationPresenter;
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
