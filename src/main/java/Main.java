import api.texttospeechservice.TextToSpeechService;
import dataaccess.DataAccessConfig;
import dataaccess.DatabaseInitializer;
import usecase.acceptapplication.AcceptApplicationController;
import usecase.acceptapplication.AcceptApplicationUseCaseFactory;
import usecase.createapplication.CreateApplicationController;
import usecase.createapplication.CreateApplicationUseCaseFactory;
import usecase.createproject.CreateProjectController;
import usecase.createproject.CreateProjectUseCaseFactory;
import usecase.createuser.CreateUserController;
import usecase.createuser.CreateUserUseCaseFactory;
import usecase.createverification.CreateVerificationController;
import usecase.createverification.CreateVerificationUseCaseFactory;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

class Main {
    public static void main(String[] args) {

        JFrame application = new JFrame("A Screen");
        application.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        application.setSize(1200, 1200);


        // Initialize text to speech credentials and files
        String serviceAccountKey = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
        if (serviceAccountKey == null) {
            throw new IllegalArgumentException("Environment variable GOOGLE_APPLICATION_CREDENTIALS is not set");
        }

        TextToSpeechService.initialize();

        application.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                System.out.println("Closing app");
                System.exit(0);
            }
        });

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
        LoginVerificationViewModel loginVerificationViewModel = new LoginVerificationViewModel();
        CreateVerificationController createVerificationController = CreateVerificationUseCaseFactory.createController(loginVerificationViewModel);
        LoginPanel loginPanel = new LoginPanel(viewManagerModel,
                                               loginPanelViewModel,
                                               loginUserController,
                                               loginVerificationViewModel,
                                               createVerificationController);

        // Search Project Panel
        SearchPanelViewModel searchPanelViewModel = new SearchPanelViewModel();
        SearchProjectController searchProjectController = SearchProjectUseCaseFactory.createSearchProjectController(searchPanelViewModel);
        GetLoggedInUserController searchPanelGetLoggedInUserController = GetLoggedInUserUseCaseFactory.create(searchPanelViewModel);
        CreateApplicationController createApplicationController = CreateApplicationUseCaseFactory.createController(searchPanelViewModel);
        SearchPanel searchPanel = new SearchPanel(viewManagerModel,searchPanelViewModel, searchProjectController, searchPanelGetLoggedInUserController, createApplicationController);

        // Add Project Panel
        AddProjectPanelViewModel addProjectPanelModel = new AddProjectPanelViewModel();
        CreateProjectController createProjectController = CreateProjectUseCaseFactory.createController(addProjectPanelModel);
        GetLoggedInUserController addProjectGetLoggedInUserController = GetLoggedInUserUseCaseFactory.create(addProjectPanelModel);
        AddProjectPanel addProjectPanel = new AddProjectPanel(viewManagerModel, addProjectPanelModel, createProjectController, addProjectGetLoggedInUserController);

        // My Projects Panel
        MyProjectsPanelViewModel myProjectsViewModel = new MyProjectsPanelViewModel();
        GetLoggedInUserController getLoggedInUserController = GetLoggedInUserUseCaseFactory.create(myProjectsViewModel);
        GetProjectsController getProjectsController = GetProjectsUseCaseFactory.createGetProjectsController(myProjectsViewModel);
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
                editProjectPanel);

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

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(0, 1));

        SwitchViewButtonPanelViewModel switchViewButtonPanelViewModel = new SwitchViewButtonPanelViewModel();
        LogoutController logoutController = LogoutUseCaseFactory.create(switchViewButtonPanelViewModel);
        JPanel switchButtons = new SwitchViewButtonPanel(viewManagerModel, switchViewButtonPanelViewModel, logoutController);

        JPanel settingsPanel = new SettingsPanel();

        bottomPanel.add(switchButtons);
        bottomPanel.add(settingsPanel);

        viewManagerModel.setActiveView(createUserPanelViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
        viewManagerModel.logout();

        application.getContentPane().add(views, BorderLayout.CENTER);
        application.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        application.pack();
        application.setVisible(true);

    }
}