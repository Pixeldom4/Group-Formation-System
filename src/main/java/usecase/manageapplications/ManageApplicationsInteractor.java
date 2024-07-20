package usecase.manageapplications;

import dataaccess.*;
import entities.Application;
import entities.User;

import java.util.HashSet;

public class ManageApplicationsInteractor implements ManageApplicationsInputBoundary {
    private final IApplicationRepository applicationRepository;
    private final IUserProjectsRepository userProjectsRepository;
    private final IUserRepository userRepository;
    private final ManageApplicationsPresenter manageApplicationsPresenter;

    public ManageApplicationsInteractor(ManageApplicationsPresenter manageApplicationsPresenter){
       this.manageApplicationsPresenter = manageApplicationsPresenter;
       this.applicationRepository = DataAccessConfig.getApplicationRepository();
       this.userProjectsRepository = DataAccessConfig.getUserProjectsRepository();
       this.userRepository = DataAccessConfig.getUserRepository();
    }

    @Override
    public void getApplicationsForProject(ManageApplicationsInputData inputData) {
        HashSet<Application> applications = applicationRepository.getApplicationsForProject(inputData.getProjectId());
        Object[][] applicationsData = new Object[applications.size()][4];
        int count = 0;
        for (Application application : applications){
            User user = userRepository.getUserById(application.getSenderUserId());
            applicationsData[count][0] = user.getFirstName() + " " + user.getLastName();
            applicationsData[count][1] = application.getSenderUserId();
            applicationsData[count][2] = application.getText();
            applicationsData[count][3] = application.getPdfBytes();
            System.out.println(user.getFirstName() + " " + user.getLastName());
            count++;
        }
        manageApplicationsPresenter.prepareSuccessProjectApplicationView(new ManageApplicationsOutputData(applicationsData));
    }

    @Override
    public void getApplicationsForSelf(ManageApplicationsInputData inputData) {
        HashSet<Application> applications = applicationRepository.getApplicationsForUser(inputData.getUserId());
//        manageApplicationsPresenter.prepareSuccessSelfApplicationView(new ManageApplicationsOutputData(applications));
    }

    @Override
    public void rejectApplicant(ManageApplicationsInputData inputData) {
        applicationRepository.deleteApplication(inputData.getUserId(), inputData.getProjectId());
        manageApplicationsPresenter.prepareSuccessDecisionView("Rejected Applicant");
    }

    @Override
    public void acceptApplicant(ManageApplicationsInputData inputData) {
        int userId = inputData.getUserId();
        int projectId = inputData.getProjectId();
        userProjectsRepository.addUserToProject(userId, projectId);
        applicationRepository.deleteApplication(userId, projectId);
        manageApplicationsPresenter.prepareSuccessDecisionView("Accepted Applicant");
    }
}
