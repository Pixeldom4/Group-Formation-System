package usecase.acceptapplication;

import dataaccess.DataAccessConfig;
import dataaccess.IApplicationRepository;
import dataaccess.IUserProjectsRepository;
import dataaccess.IUserRepository;

/**
 * Interactor class for accepting applications.
 * Implements the input boundary to handle application acceptance logic.
 */
public class AcceptApplicationInteractor implements AcceptApplicationInputBoundary {
    private final IApplicationRepository applicationRepository;
    private final IUserProjectsRepository userProjectsRepository;
    private final IUserRepository userRepository;
    private final AcceptApplicationOutputBoundary acceptApplicationPresenter;

    /**
     * Constructs an AcceptApplicationInteractor object with the specified presenter.
     *
     * @param manageApplicationsPresenter the presenter to handle output.
     */
    public AcceptApplicationInteractor(AcceptApplicationOutputBoundary manageApplicationsPresenter){
        this.acceptApplicationPresenter = manageApplicationsPresenter;
        this.applicationRepository = DataAccessConfig.getApplicationRepository();
        this.userProjectsRepository = DataAccessConfig.getUserProjectsRepository();
        this.userRepository = DataAccessConfig.getUserRepository();
    }

    /**
     * Accepts an applicant for a project.
     *
     * @param inputData the input data containing project and user IDs.
     */
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
