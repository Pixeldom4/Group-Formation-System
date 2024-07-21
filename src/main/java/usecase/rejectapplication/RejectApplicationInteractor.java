package usecase.rejectapplication;

import dataaccess.DataAccessConfig;
import dataaccess.IApplicationRepository;
import dataaccess.IUserProjectsRepository;
import dataaccess.IUserRepository;

public class RejectApplicationInteractor implements RejectApplicationInputBoundary {
    private final IApplicationRepository applicationRepository;
    private final IUserRepository userRepository;
    private final RejectApplicationOutputBoundary rejectApplicationPresenter;

    public RejectApplicationInteractor(RejectApplicationOutputBoundary manageApplicationsPresenter){
       this.rejectApplicationPresenter = manageApplicationsPresenter;
       this.applicationRepository = DataAccessConfig.getApplicationRepository();
       this.userRepository = DataAccessConfig.getUserRepository();
    }

    @Override
    public void rejectApplicant(RejectApplicationInputData inputData) {
        int userId = inputData.getUserId();
        int projectId = inputData.getProjectId();
        applicationRepository.deleteApplication(userId, projectId);
        String senderName = userRepository.getUserById(userId).getFirstName() + " " + userRepository.getUserById(userId).getLastName();
        rejectApplicationPresenter.prepareSuccessView(new RejectApplicationOutputData(senderName));
    }
}
