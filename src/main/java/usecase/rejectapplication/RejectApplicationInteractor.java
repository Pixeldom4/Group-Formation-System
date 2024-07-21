package usecase.rejectapplication;

import dataaccess.DataAccessConfig;
import dataaccess.IApplicationRepository;
import dataaccess.IUserRepository;

/**
 * Interactor class for rejecting applications.
 * Implements the input boundary to handle application rejection logic.
 */
public class RejectApplicationInteractor implements RejectApplicationInputBoundary {
    private final IApplicationRepository applicationRepository;
    private final IUserRepository userRepository;
    private final RejectApplicationOutputBoundary rejectApplicationPresenter;

    /**
     * Constructs a RejectApplicationInteractor with the specified presenter.
     *
     * @param rejectApplicationPresenter the presenter to handle output.
     */
    public RejectApplicationInteractor(RejectApplicationOutputBoundary rejectApplicationPresenter) {
        this.rejectApplicationPresenter = rejectApplicationPresenter;
        this.applicationRepository = DataAccessConfig.getApplicationRepository();
        this.userRepository = DataAccessConfig.getUserRepository();
    }

    /**
     * Rejects an applicant for a specific project.
     *
     * @param inputData the input data required to reject an applicant.
     */
    @Override
    public void rejectApplicant(RejectApplicationInputData inputData) {
        int userId = inputData.getUserId();
        int projectId = inputData.getProjectId();
        applicationRepository.deleteApplication(userId, projectId);
        String senderName = userRepository.getUserById(userId).getFirstName() + " " + userRepository.getUserById(userId).getLastName();
        rejectApplicationPresenter.prepareSuccessView(new RejectApplicationOutputData(senderName));
    }
}
