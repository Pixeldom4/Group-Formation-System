import api.texttospeechservice.TextToSpeechService;
import config.DataAccessConfig;
import usecase.createverification.CreateVerificationController;
import usecase.createverification.CreateVerificationUseCaseFactory;
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
import usecase.manageapplications.getapplications.GetApplicationsController;
import usecase.manageapplications.getapplications.GetApplicationsUseCaseFactory;
import usecase.manageusers.getloggedinuser.LoggedInDataAccessViewModel;
import usecase.manageusers.getusers.GetUsersController;
import usecase.manageusers.getusers.GetUsersInteractor;
import usecase.manageusers.getusers.GetUsersPresenter;
import usecase.manageusers.getusers.GetUsersOutputBoundary;
import usecase.manageusers.getusers.GetUsersInputBoundary;
import usecase.loginuser.LoginUserController;
import usecase.loginuser.LoginUserUseCaseFactory;
import usecase.logout.LogoutController;
import usecase.logout.LogoutUseCaseFactory;
import usecase.manageapplications.ManageApplicationsController;
import usecase.manageapplications.ManageApplicationsUseCaseFactory;
import usecase.manageapplications.createapplication.CreateApplicationController;
import usecase.manageapplications.createapplication.CreateApplicationUseCaseFactory;
import usecase.manageprojects.ManageProjectsController;
import usecase.manageprojects.ManageProjectsUseCaseFactory;
import usecase.manageprojects.editproject.EditProjectController;
import usecase.manageprojects.editproject.EditProjectUseCaseFactory;
import usecase.manageusers.ManageUsersController;
import usecase.manageusers.ManageUsersUseCaseFactory;
import usecase.manageusers.getusers.GetUsersInteractor;
import usecase.manageusers.getusers.GetUsersPresenter;
=======
import usecase.manageusers.getloggedinuser.GetLoggedInUserController;
import usecase.manageusers.getloggedinuser.GetLoggedInUserUseCaseFactory;
import usecase.searchforproject.SearchProjectController;
import usecase.searchforproject.SearchProjectUseCaseFactory;
import usecase.manageusers.getusers.GetUsersController;
import view.*;
import viewmodel.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
=======
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("FieldCanBeLocal")
class Main {
    public static void main(String[] args) {

        JFrame application = new JFrame("A Screen");
        application.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        application.setSize(1200, 1200);


        // Initialize text to speech credentials and files
        String serviceAccountKey = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
        if (serviceAccountKey == null) {
            System.err.println("Environment variable GOOGLE_APPLICATION_CREDENTIALS is not set");
        }

        TextToSpeechService.initialize();

        application.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                TextToSpeechService.shutdown();
                System.out.println("Closing app");
                System.exit(0);
            }
        });

        // Print which data access implementation is used
        System.out.println(DataAccessConfig.getImplementation());

        printLoadingBar(60, "Starting app");

        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        application.add(views);

        ViewManagerModel viewManagerModel = new ViewManagerModel();
        ViewManager viewManager = new ViewManager(views, cardLayout, viewManagerModel);

        // Create User Panel
        printLoadingBar(70, "Creating user panel");

        SearchPanelViewModel searchPanelViewModel = new SearchPanelViewModel();
        CreateUserPanelViewModel createUserPanelViewModel = new CreateUserPanelViewModel();
        EditProfileViewModel editProfileViewModel = new EditProfileViewModel();
        ManageUsersController manageUsersController = ManageUsersUseCaseFactory.create(createUserPanelViewModel, editProfileViewModel, searchPanelViewModel);

        CreateUserPanel createUserPanel = new CreateUserPanel(createUserPanelViewModel, manageUsersController);

        // Login Panel
        printLoadingBar(80, "Creating login panel");
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
        printLoadingBar(92, "Creating search panel");
        SearchProjectController searchProjectController = SearchProjectUseCaseFactory.createSearchProjectController(searchPanelViewModel);
        GetLoggedInUserController searchPanelGetLoggedInUserController = GetLoggedInUserUseCaseFactory.create(searchPanelViewModel);
        CreateApplicationController createApplicationController = CreateApplicationUseCaseFactory.createController(searchPanelViewModel);
        SearchPanel searchPanel = new SearchPanel(viewManagerModel, searchPanelViewModel, searchProjectController, searchPanelGetLoggedInUserController, createApplicationController);


        // Manage Projects
        printLoadingBar(100, "Creating add project panel");
        AddProjectPanelViewModel addProjectPanelModel = new AddProjectPanelViewModel();
        EditProjectPanelViewModel editProjectPanelViewModel = new EditProjectPanelViewModel();
        MyProjectsPanelViewModel myProjectsViewModel = new MyProjectsPanelViewModel();
        ManageProjectsController manageProjectsController = ManageProjectsUseCaseFactory.createController(addProjectPanelModel, editProjectPanelViewModel, myProjectsViewModel);

        // Add Project Panel
        GetLoggedInUserController addProjectGetLoggedInUserController = GetLoggedInUserUseCaseFactory.create(addProjectPanelModel);
        AddProjectPanel addProjectPanel = new AddProjectPanel(viewManagerModel, addProjectPanelModel, manageProjectsController, addProjectGetLoggedInUserController);

        // Display Project Application View
        printLoadingBar(111, "Creating project app view");
        DisplayProjectApplicationViewModel displayProjectApplicationViewModel = new DisplayProjectApplicationViewModel();
        GetApplicationsController getApplicationsController = GetApplicationsUseCaseFactory.createController(displayProjectApplicationViewModel);
        ManageApplicationsController manageApplicationsController = ManageApplicationsUseCaseFactory.createController(displayProjectApplicationViewModel);

        // Edit Project Panel
        printLoadingBar(116, "Creating edit project panel");
        EditProjectController editProjectController = EditProjectUseCaseFactory.createController(editProjectPanelViewModel);
        EditProjectPanel editProjectPanel = new EditProjectPanel(
                editProjectPanelViewModel,
                editProjectController,
                manageApplicationsController,
                manageProjectsController,
                displayProjectApplicationViewModel
        );

        // Instantiate GetUsersPresenter and GetUsersInteractor
        GetUsersPresenter getUsersPresenter = new GetUsersPresenter(myProjectsViewModel);
        GetUsersInteractor getUsersInteractor = new GetUsersInteractor(
                DataAccessConfig.getUserProjectsRepository(),
                DataAccessConfig.getUserRepository(),
                DataAccessConfig.getProjectRepository(),
                getUsersPresenter
        );

        GetUsersController getUsersController = new GetUsersController(getUsersInteractor);

        // My Projects Panel
        printLoadingBar(127, "Creating my projects panel");
        GetLoggedInUserController getLoggedInUserController = GetLoggedInUserUseCaseFactory.create(myProjectsViewModel);
        MyProjectsPanel myProjectsPanel = new MyProjectsPanel(
                myProjectsViewModel,
                viewManagerModel,
                getLoggedInUserController,
                getProjectsController,
                getApplicationsController,
                editProjectPanelViewModel,
                editProjectPanel,
                getUsersController);

        // Edit Profile Panel
        printLoadingBar(138, "Creating edit profile panel");
        GetLoggedInUserController myProfileGetLoggedInUserController = GetLoggedInUserUseCaseFactory.create(editProfileViewModel);

        EditProfilePanel editProfilePanel = new EditProfilePanel(
                viewManagerModel,
                manageUsersController,
                myProfileGetLoggedInUserController,
                editProfileViewModel
        );

        // add views to card layout
        printLoadingBar(149, "Adding views to card layout");
        views.add(createUserPanel, createUserPanelViewModel.getViewName());
        views.add(loginPanel, loginPanelViewModel.getViewName());
        views.add(searchPanel, searchPanelViewModel.getViewName());
        views.add(addProjectPanel, addProjectPanelModel.getViewName());
        views.add(myProjectsPanel, myProjectsViewModel.getViewName());
        views.add(editProfilePanel, editProfileViewModel.getViewName());

        // Bottom panels (switch view and settings)
        printLoadingBar(158, "Creating bottom panels");
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
        System.out.println("Loading complete!");

    }

    /**
     * Print a loading bar to the console
     * @param n the line number
     * @param taskMessage the message to display
     */
    private static void printLoadingBar(int n, String taskMessage) {
        int width = 50;

        // Progress based on line number
        int start = 60;
        int end = 180;
        int prog = n - start;
        int total = end - start;
        int i = prog * width / total;

        System.out.print("\r" + "Loading app" + ": [");
        System.out.print("=".repeat(i));
        System.out.print(" ".repeat(width - i));
        System.out.print("] " + prog * 100 / total + "% ");
        System.out.print(taskMessage + "\r");
    }
}