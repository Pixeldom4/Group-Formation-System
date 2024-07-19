package usecase.removeuserfromproject;

import dataaccess.IUserProjectsRepository;

/**
 * Interactor for the remove user from project use case.
 * Handles the business logic for creating a project.
 */
public class RemoveUserFromProjectInteractor implements RemoveUserFromProjectInputBoundary {
    private final IUserProjectsRepository userProjectsRepository;
    private final RemoveUserFromProjectPresenter removeUserFromProjectPresenter;

    /**
     * Constructs a RemoveUserFromProjectInteractor with the specified repository and presenter.
     *
     * @param removeUserFromProjectPresenter the repository to interact with the database.
     * @param userProjectsRepository the presenter to handle the output presentation.
     */
    public RemoveUserFromProjectInteractor(IUserProjectsRepository userProjectsRepository, RemoveUserFromProjectPresenter removeUserFromProjectPresenter) {
        this.removeUserFromProjectPresenter = removeUserFromProjectPresenter;
        this.userProjectsRepository = userProjectsRepository;
    };

    /**
     * Removes a user from the project.
     *
     * @param inputData the input data required to remove a user from a project.
     */
    @Override
    public void removeUserFromProject(RemoveUserFromProjectInputData inputData) {
        int userId = inputData.getUserId();
        int projectId = inputData.getProjectId();

        if (userProjectsRepository.removeUserFromProject(userId, projectId)) {
            RemoveUserFromProjectOutputData outputData = new RemoveUserFromProjectOutputData(projectId, userId);
            removeUserFromProjectPresenter.prepareSuccessView(outputData);
        } else {
            removeUserFromProjectPresenter.prepareFailView("Failed to remove user.");
        }

    }
}
