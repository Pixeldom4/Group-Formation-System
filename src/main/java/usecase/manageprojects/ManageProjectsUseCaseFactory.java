package usecase.manageprojects;

import api.embeddingapi.EmbeddingAPIInterface;
import api.embeddingapi.OpenAPIDataEmbed;
import config.DataAccessConfig;
import dataaccess.IProjectRepository;
import dataaccess.IUserProjectsRepository;
import dataaccess.ILoginUserDetails;
import usecase.manageprojects.createproject.CreateProjectInputBoundary;
import usecase.manageprojects.createproject.CreateProjectInteractor;
import usecase.manageprojects.createproject.CreateProjectOutputBoundary;
import usecase.manageprojects.createproject.CreateProjectPresenter;
import usecase.manageprojects.deleteproject.DeleteProjectInputBoundary;
import usecase.manageprojects.deleteproject.DeleteProjectInteractor;
import usecase.manageprojects.deleteproject.DeleteProjectOutputBoundary;
import usecase.manageprojects.deleteproject.DeleteProjectPresenter;
import usecase.manageprojects.editproject.EditProjectInputBoundary;
import usecase.manageprojects.editproject.EditProjectInteractor;
import usecase.manageprojects.editproject.EditProjectOutputBoundary;
import usecase.manageprojects.editproject.EditProjectPresenter;
import usecase.manageprojects.getprojects.GetProjectsInputBoundary;
import usecase.manageprojects.getprojects.GetProjectsInteractor;
import usecase.manageprojects.getprojects.GetProjectsOutputBoundary;
import usecase.manageprojects.getprojects.GetProjectsPresenter;
import viewmodel.AddProjectPanelViewModel;
import viewmodel.EditProjectPanelViewModel;
import viewmodel.MyProjectsPanelViewModel;

public class ManageProjectsUseCaseFactory {
    private static final IProjectRepository projectRepository = DataAccessConfig.getProjectRepository();
    private static final IUserProjectsRepository userProjectsRepository = DataAccessConfig.getUserProjectsRepository();
    private static final ILoginUserDetails loginUserDetails = DataAccessConfig.getLoginUserDetails();
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
        GetProjectsInputBoundary getProjectsInteractor = new GetProjectsInteractor(getProjectsPresenter, userProjectsRepository, projectRepository);
        CreateProjectOutputBoundary createProjectPresenter = new CreateProjectPresenter(addProjectPanelViewModel);
        CreateProjectInputBoundary createProjectInteractor = new CreateProjectInteractor(projectRepository, userProjectsRepository,
                                                                                         createProjectPresenter, embeddingAPI);
        EditProjectOutputBoundary editProjectPresenter = new EditProjectPresenter(editProjectPanelViewModel);
        EditProjectInputBoundary editProjectInteractor = new EditProjectInteractor(projectRepository, editProjectPresenter, embeddingAPI);
        DeleteProjectOutputBoundary deleteProjectPresenter = new DeleteProjectPresenter(myProjectsPanelViewModel);
        DeleteProjectInputBoundary deleteProjectInteractor = new DeleteProjectInteractor(deleteProjectPresenter, projectRepository, loginUserDetails);

        return new ManageProjectsController(getProjectsInteractor, createProjectInteractor, editProjectInteractor, deleteProjectInteractor);
    }
}
