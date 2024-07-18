package usecase.logout;

import dataaccess.DataAccessConfig;
import dataaccess.ILoginUserDetails;

public class LogoutInteractor implements LogoutInputBoundary {
    private final ILoginUserDetails loginUserDetails = DataAccessConfig.getLoginUserDetails();
    private final LogoutOutputBoundary logoutPresenter;

    public LogoutInteractor(LogoutOutputBoundary logoutPresenter) {
        this.logoutPresenter = logoutPresenter;
    }

    @Override
    public void logout() {
        loginUserDetails.logout();
        logoutPresenter.logoutSuccess();
    }
}
