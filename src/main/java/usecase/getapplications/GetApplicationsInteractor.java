package usecase.getapplications;

import dataaccess.DataAccessConfig;
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
     * @param applicationRepository the repository to handle application data.
     * @param userRepository the repository to handle user data.
     */
    public GetApplicationsInteractor(GetApplicationsOutputBoundary manageApplicationsPresenter,
                                     IApplicationRepository applicationRepository,
                                     IUserRepository userRepository) {
        this.manageApplicationsPresenter = manageApplicationsPresenter;
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
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
