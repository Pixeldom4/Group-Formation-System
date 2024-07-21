package usecase.logout;

import viewmodel.SwitchViewButtonPanelViewModel;

/**
 * Factory class for creating instances of the Logout use case.
 */
public class LogoutUseCaseFactory {

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
        LogoutInteractor logoutInteractor = new LogoutInteractor(logoutPresenter);
        return new LogoutController(logoutInteractor);
    }
}
