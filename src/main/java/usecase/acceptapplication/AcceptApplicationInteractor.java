package usecase.acceptapplication;

import dataaccess.DataAccessConfig;
import dataaccess.IApplicationRepository;
import dataaccess.IUserProjectsRepository;
import dataaccess.IUserRepository;

public class AcceptApplicationInteractor implements AcceptApplicationInputBoundary {
    private final IApplicationRepository applicationRepository;
    private final IUserProjectsRepository userProjectsRepository;
    private final IUserRepository userRepository;
    private final AcceptApplicationOutputBoundary acceptApplicationPresenter;

    public AcceptApplicationInteractor(AcceptApplicationOutputBoundary manageApplicationsPresenter){
       this.acceptApplicationPresenter = manageApplicationsPresenter;
       this.applicationRepository = DataAccessConfig.getApplicationRepository();
       this.userProjectsRepository = DataAccessConfig.getUserProjectsRepository();
       this.userRepository = DataAccessConfig.getUserRepository();
    }

    @Override
    public void acceptApplicant(AcceptApplicationInputData inputData) {
        int userId = inputData.getUserId();
        int projectId = inputData.getProjectId();
        userProjectsRepository.addUserToProject(userId, projectId);
        applicationRepository.deleteApplication(userId, projectId);
        String senderName = userRepository.getUserById(userId).getFirstName() + " " + userRepository.getUserById(userId).getLastName();
        acceptApplicationPresenter.prepareSuccessView(new AcceptApplicationOutputData(senderName));
    }
}
