package usecase.deleteapplication;

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
     * @param applicationRepository      the repository to handle application data
     */
    public DeleteApplicationInteractor(DeleteApplicationPresenter deleteApplicationPresenter,
                                       IApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
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
