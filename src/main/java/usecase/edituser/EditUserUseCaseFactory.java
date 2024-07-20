package usecase.edituser;

import dataaccess.DAOImplementationConfig;
import dataaccess.ILoginUserDetails;
import dataaccess.IUserRepository;
import viewmodel.MyProfileViewModel;

public class EditUserUseCaseFactory {
    private static final IUserRepository userRepository = DAOImplementationConfig.getUserRepository();

    public static EditUserController create(MyProfileViewModel myProfileViewModel){
        EditUserOutputBoundary editUserPresenter = new EditUserPresenter(myProfileViewModel);
        EditUserInputBoundary editUserInteractor = new EditUserInteractor(editUserPresenter, userRepository);
        return new EditUserController(editUserInteractor);
    }

}
