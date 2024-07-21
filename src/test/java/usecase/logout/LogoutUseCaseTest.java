package usecase.logout;

import dataaccess.ILoginUserDetails;
import dataaccess.inmemory.LoginUserDetails;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the LogoutUseCase.
 */
public class LogoutUseCaseTest {

    private static boolean logoutCalled = false;
    private static boolean success = false;

    private final static ILoginUserDetails loginUserDetails = new LoginUserDetails();
    private final static LogoutOutputBoundary logoutPresenter = new LogoutOutputBoundary() {

        /**
         * Called when logout is successful.
         */
        @Override
        public void logoutSuccess() {
            success = true;
            logoutCalled = true;
        }

        /**
         * Called when logout fails.
         */
        @Override
        public void logoutFailed() {
            success = false;
            logoutCalled = true;
        }
    };
    private final static LogoutInteractor logoutInteractor = new LogoutInteractor(logoutPresenter, loginUserDetails);
    private final static LogoutController logoutController = new LogoutController(logoutInteractor);

    /**
     * Tests the logout functionality.
     */
    @Test
    public void testLogout() {
        loginUserDetails.login(1);
        logoutController.logout();

        await().until(() -> logoutCalled);

        assertTrue(success);
        assertEquals(0, loginUserDetails.getUserId());
    }
}
