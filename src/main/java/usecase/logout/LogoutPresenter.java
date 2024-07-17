package usecase.logout;

import viewmodel.SwitchViewButtonPanelViewModel;

public class LogoutPresenter implements LogoutOutputBoundary {
    private final SwitchViewButtonPanelViewModel switchViewButtonPanelViewModel;

    public LogoutPresenter(SwitchViewButtonPanelViewModel switchViewButtonPanelViewModel) {
        this.switchViewButtonPanelViewModel = switchViewButtonPanelViewModel;
    }

    @Override
    public void logoutSuccess() {
        switchViewButtonPanelViewModel.logout();
    }

    @Override
    public void logoutFailed() {
        switchViewButtonPanelViewModel.logoutFail();
    }
}
