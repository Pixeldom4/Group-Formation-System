package usecase.logout;

public class LogoutController {
    private final LogoutInputBoundary logoutInteractor;

    public LogoutController(LogoutInputBoundary logoutInteractor) {
        this.logoutInteractor = logoutInteractor;
    }

    public void logout() {
        logoutInteractor.logout();
    }
}
