package usecase.manageprojects;

import api.EmbeddingAPIInterface;
import api.OpenAPIDataEmbed;
import dataaccess.DataAccessConfig;
import dataaccess.IProjectRepository;
import dataaccess.IUserProjectsRepository;
import usecase.createproject.*;
import usecase.deleteproject.*;
import usecase.editproject.*;
import usecase.getprojects.*;
import viewmodel.AddProjectPanelViewModel;
import viewmodel.EditProjectPanelViewModel;
import viewmodel.MyProjectsPanelViewModel;

public class ManageProjectsUseCaseFactory {
    private static final IProjectRepository projectRepository = DataAccessConfig.getProjectRepository();
    private static final IUserProjectsRepository userProjectsRepository = DataAccessConfig.getUserProjectsRepository();
    private static final EmbeddingAPIInterface embeddingAPI = new OpenAPIDataEmbed();

    // Private constructor to prevent instantiation
    private ManageProjectsUseCaseFactory() {}

    /**
     * Creates a new ManageProjectsController with the given AddProjectPanelViewModel, EditProjectPanelViewModel, and MyProjectsPanelViewModel.
     *
     * @param addProjectPanelViewModel the AddProjectPanelViewModel
     * @param editProjectPanelViewModel the EditProjectPanelViewModel
     * @param myProjectsPanelViewModel the MyProjectsPanelViewModel
     * @return a new CreateProjectController
     */
    public static ManageProjectsController createController(
            AddProjectPanelViewModel addProjectPanelViewModel,
            EditProjectPanelViewModel editProjectPanelViewModel,
            MyProjectsPanelViewModel myProjectsPanelViewModel
    ) {
        GetProjectsOutputBoundary getProjectsPresenter = new GetProjectsPresenter(myProjectsPanelViewModel);
        GetProjectsInputBoundary getProjectsInteractor = new GetProjectsInteractor(getProjectsPresenter);
        CreateProjectOutputBoundary createProjectPresenter = new CreateProjectPresenter(addProjectPanelViewModel);
        CreateProjectInputBoundary createProjectInteractor = new CreateProjectInteractor(projectRepository, userProjectsRepository, createProjectPresenter);
        EditProjectOutputBoundary editProjectPresenter = new EditProjectPresenter(editProjectPanelViewModel);
        EditProjectInputBoundary editProjectInteractor = new EditProjectInteractor(projectRepository, editProjectPresenter, embeddingAPI);
        DeleteProjectOutputBoundary deleteProjectPresenter = new DeleteProjectPresenter(myProjectsPanelViewModel);
        DeleteProjectInputBoundary deleteProjectInteractor = new DeleteProjectInteractor(deleteProjectPresenter);

        return new ManageProjectsController(getProjectsInteractor, createProjectInteractor, editProjectInteractor, deleteProjectInteractor);
    }
}
