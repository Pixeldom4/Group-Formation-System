package usecase.logout;

import dataaccess.DataAccessConfig;
import dataaccess.ILoginUserDetails;

public class LogoutInteractor implements LogoutInputBoundary {
    private ILoginUserDetails loginUserDetails = DataAccessConfig.getLoginUserDetails();
    private final LogoutOutputBoundary logoutPresenter;

    public LogoutInteractor(LogoutOutputBoundary logoutPresenter) {
        this.logoutPresenter = logoutPresenter;
    }

    public LogoutInteractor(LogoutOutputBoundary logoutPresenter, ILoginUserDetails loginUserDetails) {
        this.logoutPresenter = logoutPresenter;
        this.loginUserDetails = loginUserDetails;
    }

    @Override
    public void logout() {
        loginUserDetails.logout();
        logoutPresenter.logoutSuccess();
    }
}
