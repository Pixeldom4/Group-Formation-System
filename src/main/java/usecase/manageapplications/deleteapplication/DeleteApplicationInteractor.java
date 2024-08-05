package usecase.manageapplications.deleteapplication;

import dataaccess.DataAccessConfig;
import dataaccess.IApplicationRepository;

/**
 * Interactor for deleting an application.
 * Implements the DeleteApplicationInputBoundary interface.
 */
public class DeleteApplicationInteractor implements DeleteApplicationInputBoundary {
    private final IApplicationRepository applicationRepository;
    private final DeleteApplicationPresenter deleteApplicationPresenter;

    /**
     * Constructs a new DeleteApplicationInteractor instance.
     *
     * @param deleteApplicationPresenter the presenter to handle the output data
     */
    public DeleteApplicationInteractor(DeleteApplicationPresenter deleteApplicationPresenter) {
        this.applicationRepository = DataAccessConfig.getApplicationRepository();
        this.deleteApplicationPresenter = deleteApplicationPresenter;
    }

    /**
     * Deletes an application based on the provided input data.
     *
     * @param inputData the input data containing the sender user ID and project ID
     */
    @Override
    public void deleteApplication(DeleteApplicationInputData inputData) {
        int senderUserId = inputData.getSenderUserId();
        int projectId = inputData.getProjectId();
        applicationRepository.deleteApplication(senderUserId, projectId);
        deleteApplicationPresenter.prepareSuccessView(new DeleteApplicationOutputData(senderUserId, projectId));
    }
}
