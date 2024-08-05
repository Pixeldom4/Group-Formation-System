import dataaccess.DataAccessConfig;
import usecase.manageapplications.createapplication.CreateApplicationController;
import usecase.manageapplications.createapplication.CreateApplicationUseCaseFactory;
import usecase.manageprojects.createproject.CreateProjectController;
import usecase.manageprojects.createproject.CreateProjectUseCaseFactory;
import usecase.manageusers.createuser.CreateUserController;
import usecase.manageusers.createuser.CreateUserUseCaseFactory;
import usecase.manageprojects.deleteproject.DeleteProjectController;
import usecase.manageprojects.deleteproject.DeleteProjectUseCaseFactory;
import usecase.manageprojects.editproject.EditProjectController;
import usecase.manageprojects.editproject.EditProjectUseCaseFactory;
import usecase.manageusers.edituser.EditUserController;
import usecase.manageusers.edituser.EditUserUseCaseFactory;
import usecase.manageusers.getloggedinuser.GetLoggedInUserController;
import usecase.manageusers.getloggedinuser.GetLoggedInUserUseCaseFactory;
import usecase.manageprojects.getprojects.GetProjectsController;
import usecase.manageprojects.getprojects.GetProjectsUseCaseFactory;
import usecase.loginuser.LoginUserController;
import usecase.loginuser.LoginUserUseCaseFactory;
import usecase.logout.LogoutController;
import usecase.logout.LogoutUseCaseFactory;
import usecase.manageapplications.ManageApplicationsController;
import usecase.manageapplications.ManageApplicationsUseCaseFactory;
import usecase.manageprojects.ManageProjectsController;
import usecase.manageprojects.ManageProjectsUseCaseFactory;
import usecase.manageusers.ManageUsersController;
import usecase.manageusers.ManageUsersUseCaseFactory;
import usecase.searchforproject.SearchProjectController;
import usecase.searchforproject.SearchProjectUseCaseFactory;
import view.*;
import viewmodel.*;

import javax.swing.*;
import java.awt.*;

class Main {
    public static void main(String[] args) {

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

        SearchPanelViewModel searchPanelViewModel = new SearchPanelViewModel();

        // Manage Users
        CreateUserPanelViewModel createUserPanelViewModel = new CreateUserPanelViewModel();
        EditProfileViewModel editProfileViewModel = new EditProfileViewModel();
        ManageUsersController manageUsersController = ManageUsersUseCaseFactory.create(createUserPanelViewModel, editProfileViewModel, searchPanelViewModel);

        // Create User Panel
        CreateUserController createUserController = CreateUserUseCaseFactory.create(createUserPanelViewModel);
        CreateUserPanel createUserPanel = new CreateUserPanel(createUserPanelViewModel, manageUsersController);

        // Login Panel
        LoginPanelViewModel loginPanelViewModel = new LoginPanelViewModel();
        LoginUserController loginUserController = LoginUserUseCaseFactory.create(loginPanelViewModel);
        LoginVerificationViewModel loginVerificationViewModel = new LoginVerificationViewModel();
        CreateVerificationController createVerificationController = CreateVerificationUseCaseFactory.createController(loginVerificationViewModel);
        LoginPanel loginPanel = new LoginPanel(viewManagerModel,
                                               loginPanelViewModel,
                                               loginUserController,
                                               loginVerificationViewModel,
                                               createVerificationController);

        // Search Project Panel
        SearchProjectController searchProjectController = SearchProjectUseCaseFactory.createSearchProjectController(searchPanelViewModel);
        GetLoggedInUserController searchPanelGetLoggedInUserController = GetLoggedInUserUseCaseFactory.create(searchPanelViewModel);
        CreateApplicationController createApplicationController = CreateApplicationUseCaseFactory.createController(searchPanelViewModel);
        SearchPanel searchPanel = new SearchPanel(viewManagerModel,searchPanelViewModel, searchProjectController, searchPanelGetLoggedInUserController, createApplicationController);


        // Manage Projects
        AddProjectPanelViewModel addProjectPanelModel = new AddProjectPanelViewModel();
        EditProjectPanelViewModel editProjectPanelViewModel = new EditProjectPanelViewModel();
        MyProjectsPanelViewModel myProjectsViewModel = new MyProjectsPanelViewModel();
        ManageProjectsController manageProjectsController = ManageProjectsUseCaseFactory.createController(addProjectPanelModel, editProjectPanelViewModel, myProjectsViewModel);

        // Add Project Panel
        CreateProjectController createProjectController = CreateProjectUseCaseFactory.createController(addProjectPanelModel);
        GetLoggedInUserController addProjectGetLoggedInUserController = GetLoggedInUserUseCaseFactory.create(addProjectPanelModel);
        AddProjectPanel addProjectPanel = new AddProjectPanel(viewManagerModel, addProjectPanelModel, manageProjectsController, addProjectGetLoggedInUserController);

        // My Projects Panel
        GetLoggedInUserController getLoggedInUserController = GetLoggedInUserUseCaseFactory.create(myProjectsViewModel);
        GetProjectsController getProjectsController = GetProjectsUseCaseFactory.createGetProjectsController(myProjectsViewModel);
        DeleteProjectController deleteProjectController = DeleteProjectUseCaseFactory.createDeleteProjectController(myProjectsViewModel);

        // Display Project Application View
        DisplayProjectApplicationViewModel displayProjectApplicationViewModel = new DisplayProjectApplicationViewModel();
        ManageApplicationsController manageApplicationsController = ManageApplicationsUseCaseFactory.createController(displayProjectApplicationViewModel);
        // Edit Project Panel
        EditProjectController editProjectController = EditProjectUseCaseFactory.createController(editProjectPanelViewModel);
        EditProjectPanel editProjectPanel = new EditProjectPanel(
                editProjectPanelViewModel,
                editProjectController,
                manageApplicationsController,
                manageProjectsController,
                displayProjectApplicationViewModel
        );

        MyProjectsPanel myProjectsPanel = new MyProjectsPanel(
                myProjectsViewModel,
                viewManagerModel,
                getLoggedInUserController,
                manageProjectsController,
                editProjectPanelViewModel,
                editProjectPanel);

        // Edit Profile Panel
        EditUserController editUserController = EditUserUseCaseFactory.create(editProfileViewModel);
        GetLoggedInUserController myProfileGetLoggedInUserController = GetLoggedInUserUseCaseFactory.create(editProfileViewModel);


        EditProfilePanel editProfilePanel = new EditProfilePanel(
                viewManagerModel,
                manageUsersController,
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