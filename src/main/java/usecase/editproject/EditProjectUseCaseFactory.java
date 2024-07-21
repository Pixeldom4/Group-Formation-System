package usecase.editproject;

import api.EmbeddingAPIInterface;
import api.OpenAPIDataEmbed;
import dataaccess.DataAccessConfig;
import dataaccess.IProjectRepository;
import viewmodel.EditProjectPanelViewModel;

/**
 * Factory class for creating instances of the EditProject use case.
 */
public class EditProjectUseCaseFactory {
    private static final IProjectRepository projectRepository = DataAccessConfig.getProjectRepository();
    private static final EmbeddingAPIInterface embeddingAPI = new OpenAPIDataEmbed();

    // Private constructor to prevent instantiation
    private EditProjectUseCaseFactory() {}

    /**
     * Creates a new EditProjectController with the given EditProjectPanelViewModel.
     *
     * @param editProjectPanelViewModel the EditProjectPanelViewModel
     * @return a new EditProjectController
     */
    public static EditProjectController createController(EditProjectPanelViewModel editProjectPanelViewModel) {
        EditProjectOutputBoundary presenter = new EditProjectPresenter(editProjectPanelViewModel);
        EditProjectInputBoundary interactor = new EditProjectInteractor(projectRepository, presenter, embeddingAPI);
        return new EditProjectController(interactor);
    }
}
