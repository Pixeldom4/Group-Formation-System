package usecase.manageprojects.createproject;

import api.embeddingapi.EmbeddingAPIInterface;
import api.embeddingapi.OpenAPIDataEmbed;
import dataaccess.IProjectRepository;
import dataaccess.IUserProjectsRepository;
import viewmodel.AddProjectPanelViewModel;
import config.DataAccessConfig;

/**
 * Factory class for creating instances of the CreateProject use case.
 */
public class CreateProjectUseCaseFactory {
    private static final IProjectRepository projectRepository = DataAccessConfig.getProjectRepository();
    private static final IUserProjectsRepository userProjectsRepository = DataAccessConfig.getUserProjectsRepository();
    private static final EmbeddingAPIInterface embeddingAPI = new OpenAPIDataEmbed();

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
        CreateProjectInputBoundary inputBoundary = new CreateProjectInteractor(projectRepository, userProjectsRepository,
                                                                               outputBoundary, embeddingAPI);
        return new CreateProjectController(inputBoundary);
    }
}
