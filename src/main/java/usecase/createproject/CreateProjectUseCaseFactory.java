package usecase.createproject;

import dataaccess.DataAccessConfig;
import dataaccess.IProjectRepository;
import dataaccess.IUserProjectsRepository;
import viewmodel.AddProjectPanelViewModel;

/**
 * Factory class for creating instances of the CreateProject use case.
 */
public class CreateProjectUseCaseFactory {
    private static final IProjectRepository projectRepository = DataAccessConfig.getProjectRepository();
    private static final IUserProjectsRepository userProjectsRepository = DataAccessConfig.getUserProjectsRepository();

    // Private constructor to prevent instantiation
    private CreateProjectUseCaseFactory() {}

    /**
     * Creates a new CreateProjectController with the given AddProjectPanelViewModel.
     *
     * @param addProjectPanelViewModel the AddProjectPanelViewModel
     * @return a new CreateProjectController
     */
    public static CreateProjectController createController(AddProjectPanelViewModel addProjectPanelViewModel) {
        CreateProjectOutputBoundary outputBoundary = new CreateProjectPresenter(addProjectPanelViewModel);
        CreateProjectInputBoundary inputBoundary = new CreateProjectInteractor(projectRepository, userProjectsRepository, outputBoundary);
        return new CreateProjectController(inputBoundary);
    }
}
