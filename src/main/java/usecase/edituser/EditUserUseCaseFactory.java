package usecase.edituser;

import dataaccess.DataAccessConfig;
import dataaccess.ILoginUserDetails;
import dataaccess.IUserRepository;
import viewmodel.EditProfileViewModel;

public class EditUserUseCaseFactory {
    private static final IUserRepository userRepository = DataAccessConfig.getUserRepository();

    public static EditUserController create(EditProfileViewModel editProfileViewModel){
        EditUserOutputBoundary editUserPresenter = new EditUserPresenter(editProfileViewModel);
        EditUserInputBoundary editUserInteractor = new EditUserInteractor(editUserPresenter, userRepository);
        return new EditUserController(editUserInteractor);
    }

}
