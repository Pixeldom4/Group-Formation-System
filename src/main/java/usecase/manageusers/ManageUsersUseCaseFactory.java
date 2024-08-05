package usecase.manageusers;

import dataaccess.DataAccessConfig;
import dataaccess.IProjectRepository;
import dataaccess.IUserProjectsRepository;
import dataaccess.IUserRepository;
import org.apache.commons.lang3.NotImplementedException;
import usecase.BCryptPasswordHasher;
import usecase.PasswordHasher;
import usecase.createuser.CreateUserController;
import usecase.createuser.CreateUserInputBoundary;
import usecase.createuser.CreateUserInteractor;
import usecase.createuser.CreateUserPresenter;
import usecase.deleteuser.*;
import usecase.edituser.*;
import usecase.getloggedinuser.*;
import usecase.getusers.GetUsersInputBoundary;
import usecase.getusers.GetUsersInteractor;
import usecase.getusers.GetUsersOutputBoundary;
import usecase.getusers.GetUsersPresenter;
import viewmodel.CreateUserPanelViewModel;
import viewmodel.EditProfileViewModel;

public class ManageUsersUseCaseFactory {
    private static final IUserRepository userRepository = DataAccessConfig.getUserRepository();
    private static final IUserProjectsRepository userProjectsRepository = DataAccessConfig.getUserProjectsRepository();
    private static final IProjectRepository projectRepository = DataAccessConfig.getProjectRepository();

    // Private constructor to prevent instantiation
    private ManageUsersUseCaseFactory() {}

    public static ManageUsersController create(
            CreateUserPanelViewModel createUserViewModel,
            EditProfileViewModel editProfileViewModel,
            LoggedInDataAccessViewModel loggedInDataAccessViewModel
            ){
        CreateUserPresenter createUserPresenter = new CreateUserPresenter(createUserViewModel);
        PasswordHasher passwordHasher = new BCryptPasswordHasher();
        CreateUserInputBoundary createUserInteractor = new CreateUserInteractor(createUserPresenter, passwordHasher);
//        return new CreateUserController(interactor);

        DeleteUserPresenter deleteUserPresenter = new DeleteUserPresenter();
        DeleteUserInputBoundary deleteUserInteractor = new DeleteUserInteractor(userRepository, deleteUserPresenter);

        EditUserOutputBoundary editUserPresenter = new EditUserPresenter(editProfileViewModel);
        EditUserInputBoundary editUserInteractor = new EditUserInteractor(editUserPresenter, userRepository);
//        return new EditUserController(editUserInteractor);

        GetLoggedInUserOutputBoundary getLoggedInUserPresenter = new GetLoggedInUserPresenter(loggedInDataAccessViewModel);
        GetLoggedInUserInputBoundary getLoggedInUserInteractor = new GetLoggedInUserInteractor(getLoggedInUserPresenter);
//        return new GetLoggedInUserController(interactor);

        GetUsersOutputBoundary getUsersPresenter = new GetUsersPresenter();
        GetUsersInputBoundary getUsersInteractor = new GetUsersInteractor(userProjectsRepository, userRepository, projectRepository, getUsersPresenter);

        return new ManageUsersController(createUserInteractor, deleteUserInteractor, editUserInteractor, getLoggedInUserInteractor, getUsersInteractor);
    }

//    /**
//     * Creates a new CreateUserController with the given CreateUserPanelViewModel.
//     *
//     * @param createUserViewModel the CreateUserPanelViewModel
//     * @return a new CreateUserController
//     */
//    public static CreateUserController create(CreateUserPanelViewModel createUserViewModel) {
//        CreateUserPresenter presenter = new CreateUserPresenter(createUserViewModel);
//        PasswordHasher passwordHasher = new BCryptPasswordHasher();
//        CreateUserInputBoundary interactor = new CreateUserInteractor(presenter, passwordHasher);
//        return new CreateUserController(interactor);
//    }
//
//
//    /**
//     * Creates a new DeleteUserController with the given {something view model?}.
//     *
//     * @param {something view model} the AddProjectPanelViewModel.
//     * @return a new DeleteUserController.
//     */
//    public static DeleteUserController deleteUserController() {
//        throw new NotImplementedException();
//    }
//
//    /**
//     * Creates an instance of EditUserController with the necessary dependencies.
//     *
//     * @param editProfileViewModel The view model for the edit profile.
//     * @return An instance of EditUserController.
//     */
//    public static EditUserController create(EditProfileViewModel editProfileViewModel){
//        EditUserOutputBoundary editUserPresenter = new EditUserPresenter(editProfileViewModel);
//        EditUserInputBoundary editUserInteractor = new EditUserInteractor(editUserPresenter, userRepository);
//        return new EditUserController(editUserInteractor);
//    }
//
//    /**
//     * Creates a new GetLoggedInUserController with the given LoggedInDataAccessViewModel.
//     *
//     * @param loggedInDataAccessViewModel the LoggedInDataAccessViewModel.
//     * @return a new GetLoggedInUserController.
//     */
//    public static GetLoggedInUserController create(LoggedInDataAccessViewModel loggedInDataAccessViewModel) {
//        GetLoggedInUserOutputBoundary presenter = new GetLoggedInUserPresenter(loggedInDataAccessViewModel);
//        GetLoggedInUserInputBoundary interactor = new GetLoggedInUserInteractor(presenter);
//        return new GetLoggedInUserController(interactor);
//    }

}
