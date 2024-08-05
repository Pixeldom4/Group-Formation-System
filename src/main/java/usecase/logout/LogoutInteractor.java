package usecase.logout;

import dataaccess.ILoginUserDetails;

/**
 * Interactor class for logging out.
 * Implements the input boundary to handle the logout logic.
 */
public class LogoutInteractor implements LogoutInputBoundary {
    private final ILoginUserDetails loginUserDetails;
    private final LogoutOutputBoundary logoutPresenter;


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
