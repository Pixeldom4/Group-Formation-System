package usecase.getapplications;

import dataaccess.*;
import entities.Application;
import entities.User;

import java.util.HashSet;

public class GetApplicationsInteractor implements GetApplicationsInputBoundary {
    private final IApplicationRepository applicationRepository;
    private final IUserProjectsRepository userProjectsRepository;
    private final IUserRepository userRepository;
    private final GetApplicationsPresenter manageApplicationsPresenter;

    public GetApplicationsInteractor(GetApplicationsPresenter manageApplicationsPresenter){
       this.manageApplicationsPresenter = manageApplicationsPresenter;
       this.applicationRepository = DataAccessConfig.getApplicationRepository();
       this.userProjectsRepository = DataAccessConfig.getUserProjectsRepository();
       this.userRepository = DataAccessConfig.getUserRepository();
    }

    @Override
    public void getApplicationsForProject(GetApplicationsInputData inputData) {
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
        manageApplicationsPresenter.prepareSuccessView(new GetApplicationsOutputData(applicationsData));
    }

}
