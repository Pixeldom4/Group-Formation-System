package usecase.logout;

import viewmodel.SwitchViewButtonPanelViewModel;

/**
 * Presenter class for logging out.
 * Implements the output boundary to prepare views for logout operations.
 */
public class LogoutPresenter implements LogoutOutputBoundary {
    private final SwitchViewButtonPanelViewModel switchViewButtonPanelViewModel;

    /**
     * Constructs a LogoutPresenter with the specified view model.
     *
     * @param switchViewButtonPanelViewModel the view model to update with logout results.
     */
    public LogoutPresenter(SwitchViewButtonPanelViewModel switchViewButtonPanelViewModel) {
        this.switchViewButtonPanelViewModel = switchViewButtonPanelViewModel;
    }

    /**
     * Prepares the success view for a successful logout.
     */
    @Override
    public void logoutSuccess() {
        switchViewButtonPanelViewModel.logout();
    }

    /**
     * Prepares the failure view for a failed logout.
     */
    @Override
    public void logoutFailed() {
        switchViewButtonPanelViewModel.logoutFail();
    }
}
