package usecase.manageusers.createuser;

import config.DataAccessConfig;
import dataaccess.IUserRepository;
import usecase.BCryptPasswordHasher;
import usecase.PasswordHasher;
import viewmodel.CreateUserPanelViewModel;

/**
 * Factory class for creating instances of the CreateUser use case.
 */
public class CreateUserUseCaseFactory {
    private static final IUserRepository userRepository = DataAccessConfig.getUserRepository();

    // Private constructor to prevent instantiation
    private CreateUserUseCaseFactory() {}

    /**
     * Creates a new CreateUserController with the given CreateUserPanelViewModel.
     *
     * @param createUserViewModel the CreateUserPanelViewModel
     * @return a new CreateUserController
     */
    public static CreateUserController create(CreateUserPanelViewModel createUserViewModel) {
        CreateUserPresenter presenter = new CreateUserPresenter(createUserViewModel);
        PasswordHasher passwordHasher = new BCryptPasswordHasher();
        CreateUserInputBoundary interactor = new CreateUserInteractor(userRepository, presenter, passwordHasher);
        return new CreateUserController(interactor);
    }
}
