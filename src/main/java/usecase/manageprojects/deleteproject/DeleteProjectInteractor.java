package usecase.manageprojects.deleteproject;

import dataaccess.DataAccessConfig;
import dataaccess.ILoginUserDetails;
import dataaccess.IProjectRepository;

/**
 * Interactor class for deleting projects.
 * Implements the input boundary to handle project deletion logic.
 */
public class DeleteProjectInteractor implements DeleteProjectInputBoundary {
    private final IProjectRepository projectRepository;
    private final ILoginUserDetails loginUserDetails = DataAccessConfig.getLoginUserDetails();
    private final DeleteProjectOutputBoundary deleteProjectPresenter;

    /**
     * Constructs a DeleteProjectInteractor with the specified presenter.
     *
     * @param deleteProjectPresenter the presenter to handle output.
     */
    public DeleteProjectInteractor(DeleteProjectOutputBoundary deleteProjectPresenter) {
        this.projectRepository = DataAccessConfig.getProjectRepository();
        this.deleteProjectPresenter = deleteProjectPresenter;
    }

    /**
     * Deletes a project with the provided input data.
     *
     * @param inputData the input data containing the project ID.
     */
    @Override
    public void deleteProject(DeleteProjectInputData inputData) {
        int userId = loginUserDetails.getUserId();
        int projectId = inputData.getProjectId();
        if (projectRepository.getOwnerId(projectId) != userId) {
            deleteProjectPresenter.prepareFailView("Only the owner of the project can delete it.");
            return;
        }

        projectRepository.deleteProject(projectId);
        deleteProjectPresenter.prepareSuccessView(new DeleteProjectOutputData(projectId));
    }
}
