package usecase.logout;

/**
 * Controller class for logging out.
 * Interacts with the input boundary to process the logout operation.
 */
public class LogoutController {
    private final LogoutInputBoundary logoutInteractor;

    /**
     * Constructs a LogoutController.
     *
     * @param logoutInteractor the interactor that handles the logout use case.
     */
    public LogoutController(LogoutInputBoundary logoutInteractor) {
        this.logoutInteractor = logoutInteractor;
    }

    /**
     * Initiates the logout process.
     */
    public void logout() {
        logoutInteractor.logout();
    }
}
