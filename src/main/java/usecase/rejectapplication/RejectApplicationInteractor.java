package usecase.rejectapplication;

import dataaccess.DataAccessConfig;
import dataaccess.IApplicationRepository;
import dataaccess.IUserProjectsRepository;
import dataaccess.IUserRepository;
import entities.Application;
import entities.User;

import java.util.HashSet;

public class RejectApplicationInteractor implements RejectApplicationInputBoundary {
    private final IApplicationRepository applicationRepository;
    private final IUserProjectsRepository userProjectsRepository;
    private final IUserRepository userRepository;
    private final RejectApplicationPresenter rejectApplicationPresenter;

    public RejectApplicationInteractor(RejectApplicationPresenter manageApplicationsPresenter){
       this.rejectApplicationPresenter = manageApplicationsPresenter;
       this.applicationRepository = DataAccessConfig.getApplicationRepository();
       this.userProjectsRepository = DataAccessConfig.getUserProjectsRepository();
       this.userRepository = DataAccessConfig.getUserRepository();
    }

    @Override
    public void rejectApplicant(RejectApplicationInputData inputData) {
        applicationRepository.deleteApplication(inputData.getUserId(), inputData.getProjectId());
        rejectApplicationPresenter.prepareSuccessView(new RejectApplicationOutputData());
    }
}
