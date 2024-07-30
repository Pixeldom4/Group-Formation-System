import dataaccess.DataAccessConfig;
import dataaccess.DatabaseInitializer;
import dataaccess.database.UserRepository;
import dataaccess.database.ProjectRepository;
import dataaccess.database.manager.UserManager;
import dataaccess.database.manager.UserTagsManager;
import dataaccess.database.manager.UserProjectsManager;
import dataaccess.database.manager.ProjectManager;
import dataaccess.database.manager.ProjectTagsManager;
import dataaccess.database.manager.ProjectEmbeddingsManager;
import usecase.acceptapplication.AcceptApplicationController;
import usecase.acceptapplication.AcceptApplicationUseCaseFactory;
import usecase.createapplication.CreateApplicationController;
import usecase.createapplication.CreateApplicationUseCaseFactory;
import usecase.createproject.CreateProjectController;
import usecase.createproject.CreateProjectUseCaseFactory;
import usecase.createuser.CreateUserController;
import usecase.createuser.CreateUserUseCaseFactory;
import usecase.deleteproject.DeleteProjectController;
import usecase.deleteproject.DeleteProjectUseCaseFactory;
import usecase.editproject.EditProjectController;
import usecase.editproject.EditProjectUseCaseFactory;
import usecase.edituser.EditUserController;
import usecase.edituser.EditUserUseCaseFactory;
import usecase.getapplications.GetApplicationsController;
import usecase.getapplications.GetApplicationsUseCaseFactory;
import usecase.getloggedinuser.GetLoggedInUserController;
import usecase.getloggedinuser.GetLoggedInUserUseCaseFactory;
import usecase.getprojects.GetProjectsController;
import usecase.getprojects.GetProjectsUseCaseFactory;
import usecase.getusers.GetUsersController;
import usecase.getusers.GetUsersInteractor;
import usecase.getusers.GetUsersInputBoundary;
import usecase.getusers.GetUsersOutputBoundary;
import usecase.getusers.GetUsersPresenter;
import usecase.loginuser.LoginUserController;
import usecase.loginuser.LoginUserUseCaseFactory;
import usecase.logout.LogoutController;
import usecase.logout.LogoutUseCaseFactory;
import usecase.rejectapplication.RejectApplicationController;
import usecase.rejectapplication.RejectApplicationUseCaseFactory;
import usecase.searchforproject.SearchProjectController;
import usecase.searchforproject.SearchProjectUseCaseFactory;
import view.*;
import viewmodel.*;

import javax.swing.*;
import java.awt.*;

class Main {
    public static void main(String[] args) {
        // Initialize the database
        DatabaseInitializer.initializeDatabase();

        JFrame application = new JFrame("A Screen");
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.setSize(1200, 1200);

        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        application.add(views);

        // Print which data access implementation is used
        System.out.println(DataAccessConfig.getImplementation());

        ViewManagerModel viewManagerModel = new ViewManagerModel();
        ViewManager viewManager = new ViewManager(views, cardLayout, viewManagerModel);

        // Create User Panel
        CreateUserPanelViewModel createUserPanelViewModel = new CreateUserPanelViewModel();
        CreateUserController createUserController = CreateUserUseCaseFactory.create(createUserPanelViewModel);
        CreateUserPanel createUserPanel = new CreateUserPanel(createUserPanelViewModel, createUserController);

        // Login Panel
        LoginPanelViewModel loginPanelViewModel = new LoginPanelViewModel();
        LoginUserController loginUserController = LoginUserUseCaseFactory.create(loginPanelViewModel);
        LoginPanel loginPanel = new LoginPanel(viewManagerModel, loginPanelViewModel, loginUserController);

        // Search Project Panel
        SearchPanelViewModel searchPanelViewModel = new SearchPanelViewModel();
        SearchProjectController searchProjectController = SearchProjectUseCaseFactory.createSearchProjectController(searchPanelViewModel);
        GetLoggedInUserController searchPanelGetLoggedInUserController = GetLoggedInUserUseCaseFactory.create(searchPanelViewModel);
        CreateApplicationController createApplicationController = CreateApplicationUseCaseFactory.createController(searchPanelViewModel);
        SearchPanel searchPanel = new SearchPanel(viewManagerModel, searchPanelViewModel, searchProjectController, searchPanelGetLoggedInUserController, createApplicationController);

        // Add Project Panel
        AddProjectPanelViewModel addProjectPanelModel = new AddProjectPanelViewModel();
        CreateProjectController createProjectController = CreateProjectUseCaseFactory.createController(addProjectPanelModel);
        GetLoggedInUserController addProjectGetLoggedInUserController = GetLoggedInUserUseCaseFactory.create(addProjectPanelModel);
        AddProjectPanel addProjectPanel = new AddProjectPanel(viewManagerModel, addProjectPanelModel, createProjectController, addProjectGetLoggedInUserController);

        // My Projects Panel
        MyProjectsPanelViewModel myProjectsViewModel = new MyProjectsPanelViewModel();
        GetLoggedInUserController getLoggedInUserController = GetLoggedInUserUseCaseFactory.create(myProjectsViewModel);
        GetProjectsController getProjectsController = GetProjectsUseCaseFactory.createGetProjectsController(myProjectsViewModel);

        // Initialize User and Project Repositories and Managers
        String databaseName = "myDatabase.db"; // Ensure this matches Database.java
        UserManager userManager = new UserManager(databaseName);
        UserTagsManager userTagsManager = new UserTagsManager(databaseName);
        UserProjectsManager userProjectsManager = new UserProjectsManager(databaseName);
        UserRepository userRepository = new UserRepository(userManager, userTagsManager, userProjectsManager);

        ProjectManager projectManager = new ProjectManager(databaseName);
        ProjectTagsManager projectTagsManager = new ProjectTagsManager(databaseName);
        ProjectEmbeddingsManager projectEmbeddingsManager = new ProjectEmbeddingsManager(databaseName);
        ProjectRepository projectRepository = new ProjectRepository(projectManager, projectTagsManager, projectEmbeddingsManager, userProjectsManager);

        // Initialize GetUsers Components
        GetUsersOutputBoundary getUsersPresenter = new GetUsersPresenter();
        GetUsersInputBoundary getUsersInteractor = new GetUsersInteractor(userProjectsManager, userRepository, projectRepository, getUsersPresenter);
        GetUsersController getUsersController = new GetUsersController(getUsersInteractor);

        DeleteProjectController deleteProjectController = DeleteProjectUseCaseFactory.createDeleteProjectController(myProjectsViewModel);

        // Display Project Application View
        DisplayProjectApplicationViewModel displayProjectApplicationViewModel = new DisplayProjectApplicationViewModel();
        GetApplicationsController getApplicationsController = GetApplicationsUseCaseFactory.createController(displayProjectApplicationViewModel);
        AcceptApplicationController acceptApplicationController = AcceptApplicationUseCaseFactory.createController(displayProjectApplicationViewModel);
        RejectApplicationController rejectApplicationController = RejectApplicationUseCaseFactory.createController(displayProjectApplicationViewModel);

        // Edit Project Panel
        EditProjectPanelViewModel editProjectPanelViewModel = new EditProjectPanelViewModel();
        EditProjectController editProjectController = EditProjectUseCaseFactory.createController(editProjectPanelViewModel);
        EditProjectPanel editProjectPanel = new EditProjectPanel(
                editProjectPanelViewModel,
                editProjectController,
                getApplicationsController,
                deleteProjectController,
                displayProjectApplicationViewModel,
                acceptApplicationController,
                rejectApplicationController
        );

        MyProjectsPanel myProjectsPanel = new MyProjectsPanel(
                myProjectsViewModel,
                viewManagerModel,
                getLoggedInUserController,
                getProjectsController,
                getApplicationsController,
                editProjectPanelViewModel,
                editProjectPanel,
                getUsersController
        );

        // Edit Profile Panel
        EditProfileViewModel editProfileViewModel = new EditProfileViewModel();
        EditUserController editUserController = EditUserUseCaseFactory.create(editProfileViewModel);
        GetLoggedInUserController myProfileGetLoggedInUserController = GetLoggedInUserUseCaseFactory.create(editProfileViewModel);

        EditProfilePanel editProfilePanel = new EditProfilePanel(
                viewManagerModel,
                editUserController,
                myProfileGetLoggedInUserController,
                editProfileViewModel
        );

        // add views to card layout
        views.add(createUserPanel, createUserPanelViewModel.getViewName());
        views.add(loginPanel, loginPanelViewModel.getViewName());
        views.add(searchPanel, searchPanelViewModel.getViewName());
        views.add(addProjectPanel, addProjectPanelModel.getViewName());
        views.add(myProjectsPanel, myProjectsViewModel.getViewName());
        views.add(editProfilePanel, editProfileViewModel.getViewName());

        SwitchViewButtonPanelViewModel switchViewButtonPanelViewModel = new SwitchViewButtonPanelViewModel();
        LogoutController logoutController = LogoutUseCaseFactory.create(switchViewButtonPanelViewModel);
        JPanel switchButtons = new SwitchViewButtonPanel(viewManagerModel, switchViewButtonPanelViewModel, logoutController);

        viewManagerModel.setActiveView(createUserPanelViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
        viewManagerModel.logout();

        application.getContentPane().add(views, BorderLayout.CENTER);
        application.getContentPane().add(switchButtons, BorderLayout.SOUTH);

        application.pack();
        application.setVisible(true);
    }
}
