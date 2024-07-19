package usecase.deleteuser;

import dataaccess.DataAccessConfig;
import dataaccess.IUserRepository;
import org.apache.commons.lang3.NotImplementedException;

public class DeleteUserUseCaseFactory {
    private static final IUserRepository userRepository = DataAccessConfig.getUserRepository();

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
