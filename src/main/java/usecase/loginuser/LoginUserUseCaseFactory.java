package usecase.loginuser;

import dataaccess.DataAccessConfig;
import dataaccess.IUserRepository;
import usecase.BCryptPasswordHasher;
import usecase.PasswordHasher;
import viewmodel.LoginPanelViewModel;

public class LoginUserUseCaseFactory {
    private static final IUserRepository userRepository = DataAccessConfig.getUserRepository();
    private static final PasswordHasher passwordHasher = new BCryptPasswordHasher();

    private LoginUserUseCaseFactory() {
    }

    public static LoginUserController create(LoginPanelViewModel loginPanelViewModel) {
        LoginUserPresenter loginUserPresenter = new LoginUserPresenter(loginPanelViewModel);
        LoginAuthenticator loginAuthenticator = new LoginAuthenticatorService(userRepository, passwordHasher);
        LoginUserInteractor loginUserInteractor = new LoginUserInteractor(userRepository, loginUserPresenter, loginAuthenticator);
        return new LoginUserController(loginUserInteractor);
    }
}
