package usecase.logout;

import config.DataAccessConfig;
import dataaccess.ILoginUserDetails;

/**
 * Interactor class for logging out.
 * Implements the input boundary to handle the logout logic.
 */
public class LogoutInteractor implements LogoutInputBoundary {
    private ILoginUserDetails loginUserDetails = DataAccessConfig.getLoginUserDetails();
    private final LogoutOutputBoundary logoutPresenter;

    /**
     * Constructs a LogoutInteractor with the specified presenter.
     *
     * @param logoutPresenter the presenter to handle output.
     */
    public LogoutInteractor(LogoutOutputBoundary logoutPresenter) {
        this.logoutPresenter = logoutPresenter;
    }

    /**
     * Constructs a LogoutInteractor with the specified presenter and login user details.
     *
     * @param logoutPresenter  the presenter to handle output.
     * @param loginUserDetails the login user details.
     */
    public LogoutInteractor(LogoutOutputBoundary logoutPresenter, ILoginUserDetails loginUserDetails) {
        this.logoutPresenter = logoutPresenter;
        this.loginUserDetails = loginUserDetails;
    }

    /**
     * Initiates the logout process.
     */
    @Override
    public void logout() {
        loginUserDetails.logout();
        logoutPresenter.logoutSuccess();
    }
}
