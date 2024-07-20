package usecase.edituser;

import dataaccess.DataAccessConfig;
import dataaccess.ILoginUserDetails;
import dataaccess.IUserRepository;
import viewmodel.MyProfileViewModel;

public class EditUserUseCaseFactory {
    private static final IUserRepository userRepository = DataAccessConfig.getUserRepository();

    public static EditUserController create(MyProfileViewModel myProfileViewModel){
        EditUserOutputBoundary editUserPresenter = new EditUserPresenter(myProfileViewModel);
        EditUserInputBoundary editUserInteractor = new EditUserInteractor(editUserPresenter, userRepository);
        return new EditUserController(editUserInteractor);
    }

}
