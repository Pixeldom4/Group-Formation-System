package usecase.editproject;

import api.EmbeddingAPIInterface;
import api.OpenAPIDataEmbed;
import dataaccess.DataAccessConfig;
import dataaccess.IProjectRepository;
import viewmodel.EditProjectPanelViewModel;

public class EditProjectUseCaseFactory {
    private static IProjectRepository projectRepository = DataAccessConfig.getProjectRepository();
    private static EmbeddingAPIInterface embeddingAPI = new OpenAPIDataEmbed();

    private EditProjectUseCaseFactory() {
    }

    public static EditProjectController createController(EditProjectPanelViewModel editProjectPanelViewModel) {
        EditProjectOutputBoundary presenter = new EditProjectPresenter(editProjectPanelViewModel);
        EditProjectInputBoundary interactor = new EditProjectInteractor(projectRepository, presenter, embeddingAPI);
        return new EditProjectController(interactor);
    }
}
