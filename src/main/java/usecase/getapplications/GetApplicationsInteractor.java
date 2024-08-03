package usecase.getapplications;

import config.DataAccessConfig;
import dataaccess.IApplicationRepository;
import dataaccess.IUserRepository;
import entities.Application;
import entities.User;

import java.util.HashSet;

/**
 * Interactor class for retrieving applications.
 * Implements the input boundary to handle application retrieval logic.
 */
public class GetApplicationsInteractor implements GetApplicationsInputBoundary {
    private final IApplicationRepository applicationRepository;
    private final IUserRepository userRepository;
    private final GetApplicationsOutputBoundary manageApplicationsPresenter;

    /**
     * Constructs a GetApplicationsInteractor with the specified presenter.
     *
     * @param manageApplicationsPresenter the presenter to handle output.
     */
    public GetApplicationsInteractor(GetApplicationsOutputBoundary manageApplicationsPresenter) {
        this.manageApplicationsPresenter = manageApplicationsPresenter;
        this.applicationRepository = DataAccessConfig.getApplicationRepository();
        this.userRepository = DataAccessConfig.getUserRepository();
    }

    /**
     * Retrieves applications for a project with the provided input data.
     *
     * @param inputData the input data containing the project ID.
     */
    @Override
    public void getApplicationsForProject(GetApplicationsInputData inputData) {
        HashSet<Application> applications = applicationRepository.getApplicationsForProject(inputData.getProjectId());
        Object[][] applicationsData = new Object[applications.size()][4];
        int count = 0;
        for (Application application : applications) {
            User user = userRepository.getUserById(application.getSenderUserId());
            applicationsData[count][0] = user.getFirstName() + " " + user.getLastName();
            applicationsData[count][1] = application.getSenderUserId();
            applicationsData[count][2] = application.getText();
            applicationsData[count][3] = application.getPdfBytes();
            count++;
        }
        manageApplicationsPresenter.prepareSuccessView(new GetApplicationsOutputData(applicationsData));
    }
}
