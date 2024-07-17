package usecase.logout;

import viewmodel.SwitchViewButtonPanelViewModel;

public class LogoutUseCaseFactory {

    private LogoutUseCaseFactory() {
    }

    public static LogoutController create(SwitchViewButtonPanelViewModel switchViewButtonPanelViewModel) {
        LogoutOutputBoundary logoutPresenter = new LogoutPresenter(switchViewButtonPanelViewModel);
        LogoutInteractor logoutInteractor = new LogoutInteractor(logoutPresenter);
        return new LogoutController(logoutInteractor);
    }
}
