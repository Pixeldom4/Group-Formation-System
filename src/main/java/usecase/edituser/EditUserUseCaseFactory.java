package usecase.edituser;

import dataaccess.DataAccessConfig;
import dataaccess.ILoginUserDetails;
import dataaccess.IUserRepository;
import viewmodel.EditProfileViewModel;

/**
 * Factory class for creating instances related to the Edit User use case.
 */
public class EditUserUseCaseFactory {
    private static final IUserRepository userRepository = DataAccessConfig.getUserRepository();

    /**
     * Creates an instance of EditUserController with the necessary dependencies.
     *
     * @param editProfileViewModel The view model for the edit profile.
     * @return An instance of EditUserController.
     */
    public static EditUserController create(EditProfileViewModel editProfileViewModel){
        EditUserOutputBoundary editUserPresenter = new EditUserPresenter(editProfileViewModel);
        EditUserInputBoundary editUserInteractor = new EditUserInteractor(editUserPresenter, userRepository);
        return new EditUserController(editUserInteractor);
    }
}
