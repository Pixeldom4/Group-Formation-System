package usecase.manageusers;

import dataaccess.*;
import dataaccess.inmemory.LoginUserDetails;
import usecase.BCryptPasswordHasher;
import usecase.PasswordHasher;
import usecase.manageusers.createuser.CreateUserInputBoundary;
import usecase.manageusers.createuser.CreateUserInteractor;
import usecase.manageusers.createuser.CreateUserPresenter;
import usecase.manageusers.deleteuser.DeleteUserInputBoundary;
import usecase.manageusers.deleteuser.DeleteUserInteractor;
import usecase.manageusers.deleteuser.DeleteUserPresenter;
import usecase.manageusers.edituser.EditUserInputBoundary;
import usecase.manageusers.edituser.EditUserInteractor;
import usecase.manageusers.edituser.EditUserOutputBoundary;
import usecase.manageusers.edituser.EditUserPresenter;
import usecase.manageusers.getloggedinuser.*;
import usecase.manageusers.getusers.GetUsersInputBoundary;
import usecase.manageusers.getusers.GetUsersInteractor;
import usecase.manageusers.getusers.GetUsersOutputBoundary;
import usecase.manageusers.getusers.GetUsersPresenter;
import viewmodel.CreateUserPanelViewModel;
import viewmodel.EditProfileViewModel;

public class ManageUsersUseCaseFactory {
    private static final IUserRepository userRepository = DataAccessConfig.getUserRepository();
    private static final IUserProjectsRepository userProjectsRepository = DataAccessConfig.getUserProjectsRepository();
    private static final IProjectRepository projectRepository = DataAccessConfig.getProjectRepository();
    private static final ILoginUserDetails loginUserDetails = DataAccessConfig.getLoginUserDetails();

    // Private constructor to prevent instantiation
    private ManageUsersUseCaseFactory() {}

    public static ManageUsersController create(
            CreateUserPanelViewModel createUserViewModel,
            EditProfileViewModel editProfileViewModel,
            LoggedInDataAccessViewModel loggedInDataAccessViewModel
            ){
        CreateUserPresenter createUserPresenter = new CreateUserPresenter(createUserViewModel);
        PasswordHasher passwordHasher = new BCryptPasswordHasher();
        CreateUserInputBoundary createUserInteractor = new CreateUserInteractor(userRepository, createUserPresenter, passwordHasher);

        DeleteUserPresenter deleteUserPresenter = new DeleteUserPresenter();
        DeleteUserInputBoundary deleteUserInteractor = new DeleteUserInteractor(userRepository, deleteUserPresenter);

        EditUserOutputBoundary editUserPresenter = new EditUserPresenter(editProfileViewModel);
        EditUserInputBoundary editUserInteractor = new EditUserInteractor(editUserPresenter, userRepository);

        GetLoggedInUserOutputBoundary getLoggedInUserPresenter = new GetLoggedInUserPresenter(loggedInDataAccessViewModel);
        GetLoggedInUserInputBoundary getLoggedInUserInteractor = new GetLoggedInUserInteractor(getLoggedInUserPresenter, loginUserDetails, userRepository);

        GetUsersOutputBoundary getUsersPresenter = new GetUsersPresenter();
        GetUsersInputBoundary getUsersInteractor = new GetUsersInteractor(userProjectsRepository, userRepository, projectRepository, getUsersPresenter);

        return new ManageUsersController(createUserInteractor, deleteUserInteractor, editUserInteractor, getLoggedInUserInteractor, getUsersInteractor);
    }

}
