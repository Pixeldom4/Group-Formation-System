package usecase.manageusers.deleteuser;

import dataaccess.DataAccessConfig;
import dataaccess.IUserRepository;
import org.apache.commons.lang3.NotImplementedException;

/**
 * Factory class for creating instances of the DeleteUser use case.
 */
public class DeleteUserUseCaseFactory {
    private static final IUserRepository userRepository = DataAccessConfig.getUserRepository();

    // Private constructor to prevent instantiation
    private DeleteUserUseCaseFactory() {}

    /**
     * Creates a new DeleteUserController with the given {something view model?}.
     *
     * @param {something view model} the AddProjectPanelViewModel.
     * @return a new DeleteUserController.
     */
    public static DeleteUserController deleteUserController() {
        throw new NotImplementedException();
    }
}
