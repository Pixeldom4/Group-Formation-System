package usecase.logout;

import config.DataAccessConfig;
import dataaccess.ILoginUserDetails;
import viewmodel.SwitchViewButtonPanelViewModel;

/**
 * Factory class for creating instances of the Logout use case.
 */
public class LogoutUseCaseFactory {
    private static final ILoginUserDetails loginUserDetails = DataAccessConfig.getLoginUserDetails();

    // Private constructor to prevent instantiation
    private LogoutUseCaseFactory() {
    }

    /**
     * Creates a new LogoutController with the given SwitchViewButtonPanelViewModel.
     *
     * @param switchViewButtonPanelViewModel the SwitchViewButtonPanelViewModel.
     * @return a new LogoutController.
     */
    public static LogoutController create(SwitchViewButtonPanelViewModel switchViewButtonPanelViewModel) {
        LogoutOutputBoundary logoutPresenter = new LogoutPresenter(switchViewButtonPanelViewModel);
        LogoutInteractor logoutInteractor = new LogoutInteractor(logoutPresenter, loginUserDetails);
        return new LogoutController(logoutInteractor);
    }
}
