package usecase.logout;

/**
 * Output boundary interface for logging out.
 * Defines methods to prepare success and failure views.
 */
public interface LogoutOutputBoundary {
    /**
     * Prepares the success view for a successful logout.
     */
    void logoutSuccess();

    /**
     * Prepares the failure view for a failed logout.
     */
    void logoutFailed();
}
