package usecase.acceptapplication;

import dataaccess.IApplicationRepository;
import dataaccess.IUserProjectsRepository;
import dataaccess.IUserRepository;

/**
 * Interactor class for accepting applications.
 * Implements the input boundary to handle application acceptance logic.
 */
public class AcceptApplicationInteractor implements AcceptApplicationInputBoundary {
    protected IApplicationRepository applicationRepository;
    protected IUserProjectsRepository userProjectsRepository;
    protected IUserRepository userRepository;
    protected final AcceptApplicationOutputBoundary acceptApplicationPresenter;

    /**
     * Constructs an AcceptApplicationInteractor object with the specified presenter.
     *
     * @param manageApplicationsPresenter the presenter to handle output.
     * @param applicationRepository the repository to handle application data.
     * @param userProjectsRepository the repository to handle user-project associations.
     * @param userRepository the repository to handle user data.
     */
    public AcceptApplicationInteractor(AcceptApplicationOutputBoundary manageApplicationsPresenter,
                                       IApplicationRepository applicationRepository,
                                       IUserProjectsRepository userProjectsRepository,
                                       IUserRepository userRepository) {
        this.acceptApplicationPresenter = manageApplicationsPresenter;
        this.applicationRepository = applicationRepository;
        this.userProjectsRepository = userProjectsRepository;
        this.userRepository = userRepository;
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
