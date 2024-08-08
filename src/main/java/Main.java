import api.texttospeechservice.TextToSpeechService;
import config.DataAccessConfig;
import usecase.createverification.CreateVerificationController;
import usecase.createverification.CreateVerificationUseCaseFactory;
import usecase.loginuser.LoginUserController;
import usecase.loginuser.LoginUserUseCaseFactory;
import usecase.logout.LogoutController;
import usecase.logout.LogoutUseCaseFactory;
import usecase.manageapplications.ManageApplicationsController;
import usecase.manageapplications.ManageApplicationsUseCaseFactory;
import usecase.manageapplications.createapplication.CreateApplicationController;
import usecase.manageapplications.createapplication.CreateApplicationUseCaseFactory;
import usecase.manageapplications.getapplications.GetApplicationsController;
import usecase.manageapplications.getapplications.GetApplicationsUseCaseFactory;
import usecase.manageprojects.ManageProjectsController;
import usecase.manageprojects.ManageProjectsUseCaseFactory;
import usecase.manageprojects.editproject.EditProjectController;
import usecase.manageprojects.editproject.EditProjectUseCaseFactory;
import usecase.manageusers.ManageUsersController;
import usecase.manageusers.ManageUsersUseCaseFactory;
import usecase.manageusers.getloggedinuser.GetLoggedInUserController;
import usecase.manageusers.getloggedinuser.GetLoggedInUserUseCaseFactory;
import usecase.manageusers.getusers.GetUsersController;
import usecase.manageusers.getusers.GetUsersInteractor;
import usecase.manageusers.getusers.GetUsersPresenter;
import usecase.searchforproject.SearchProjectController;
import usecase.searchforproject.SearchProjectUseCaseFactory;
import view.*;
import viewmodel.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.CountDownLatch;

@SuppressWarnings("FieldCanBeLocal")
class Main {
    public static void main(String[] args) {

        JFrame application = new JFrame("A Screen");
        application.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        application.setSize(1200, 1200);

        CountDownLatch latch = new CountDownLatch(1);
        AppLaunchSettingView appLaunchSettingView = new AppLaunchSettingView(latch);

        try {
            latch.await();  // Wait for the settings to be confirmed
        } catch (InterruptedException e) {
            System.err.println("Interrupted while waiting for settings");
        }

        appLaunchSettingView.dispose();

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

        printLoadingBar(76, "Starting app");

        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        application.add(views);

        ViewManagerModel viewManagerModel = new ViewManagerModel();
        ViewManager viewManager = new ViewManager(views, cardLayout, viewManagerModel);

        // Create User Panel
        printLoadingBar(86, "Creating user panel");

        SearchPanelViewModel searchPanelViewModel = new SearchPanelViewModel();
        CreateUserPanelViewModel createUserPanelViewModel = new CreateUserPanelViewModel();
        EditProfileViewModel editProfileViewModel = new EditProfileViewModel();
        MyProjectsPanelViewModel myProjectsViewModel = new MyProjectsPanelViewModel();
        ManageUsersController manageUsersController = ManageUsersUseCaseFactory.create(createUserPanelViewModel, editProfileViewModel,
                                                                                       searchPanelViewModel, myProjectsViewModel);

        CreateUserPanel createUserPanel = new CreateUserPanel(createUserPanelViewModel, manageUsersController);

        // Login Panel
        printLoadingBar(98, "Creating login panel");
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
        printLoadingBar(110, "Creating search panel");
        SearchProjectController searchProjectController = SearchProjectUseCaseFactory.createSearchProjectController(searchPanelViewModel);
        GetLoggedInUserController searchPanelGetLoggedInUserController = GetLoggedInUserUseCaseFactory.create(searchPanelViewModel);
        CreateApplicationController createApplicationController = CreateApplicationUseCaseFactory.createController(searchPanelViewModel);
        SearchPanel searchPanel = new SearchPanel(viewManagerModel, searchPanelViewModel, searchProjectController, searchPanelGetLoggedInUserController, createApplicationController);


        // Manage Projects
        printLoadingBar(118, "Creating add project panel");
        AddProjectPanelViewModel addProjectPanelModel = new AddProjectPanelViewModel();
        EditProjectPanelViewModel editProjectPanelViewModel = new EditProjectPanelViewModel();
        ManageProjectsController manageProjectsController = ManageProjectsUseCaseFactory.createController(addProjectPanelModel, editProjectPanelViewModel, myProjectsViewModel);

        // Add Project Panel
        GetLoggedInUserController addProjectGetLoggedInUserController = GetLoggedInUserUseCaseFactory.create(addProjectPanelModel);
        AddProjectPanel addProjectPanel = new AddProjectPanel(viewManagerModel, addProjectPanelModel, manageProjectsController, addProjectGetLoggedInUserController);

        // Display Project Application View
        printLoadingBar(128, "Creating project app view");
        DisplayProjectApplicationViewModel displayProjectApplicationViewModel = new DisplayProjectApplicationViewModel();
        GetApplicationsController getApplicationsController = GetApplicationsUseCaseFactory.createController(displayProjectApplicationViewModel);
        ManageApplicationsController manageApplicationsController = ManageApplicationsUseCaseFactory.createController(displayProjectApplicationViewModel);

        // Edit Project Panel
        printLoadingBar(134, "Creating edit project panel");
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
        printLoadingBar(156, "Creating my projects panel");
        GetLoggedInUserController getLoggedInUserController = GetLoggedInUserUseCaseFactory.create(myProjectsViewModel);
        UsersPanel usersPanel = new UsersPanel();
        MyProjectsPanel myProjectsPanel = new MyProjectsPanel(
                myProjectsViewModel,
                viewManagerModel,
                getLoggedInUserController,
                manageProjectsController,
                manageUsersController,
                editProjectPanelViewModel,
                editProjectPanel,
                usersPanel
        );

        // Edit Profile Panel
        printLoadingBar(171, "Creating edit profile panel");
        GetLoggedInUserController myProfileGetLoggedInUserController = GetLoggedInUserUseCaseFactory.create(editProfileViewModel);

        EditProfilePanel editProfilePanel = new EditProfilePanel(
                viewManagerModel,
                manageUsersController,
                myProfileGetLoggedInUserController,
                editProfileViewModel
        );

        // add views to card layout
        printLoadingBar(182, "Adding views to card layout");
        views.add(createUserPanel, createUserPanelViewModel.getViewName());
        views.add(loginPanel, loginPanelViewModel.getViewName());
        views.add(searchPanel, searchPanelViewModel.getViewName());
        views.add(addProjectPanel, addProjectPanelModel.getViewName());
        views.add(myProjectsPanel, myProjectsViewModel.getViewName());
        views.add(editProfilePanel, editProfileViewModel.getViewName());

        // Bottom panels (switch view and settings)
        printLoadingBar(191, "Creating bottom panels");
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
        int start = 76;
        int end = 213;
        int prog = n - start;
        int total = end - start;
        int i = prog * width / total;

        System.out.print("\r" + "Loading app" + ": [");
        System.out.print("=".repeat(i));
        System.out.print(" ".repeat(width - i));
        System.out.print("] " + prog * 100 / total + "% (Remaining time: Very soon)");
        System.out.print(taskMessage + "\r");
    }
}