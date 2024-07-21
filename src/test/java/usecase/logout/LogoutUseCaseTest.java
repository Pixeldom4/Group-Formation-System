package usecase.logout;

import dataaccess.ILoginUserDetails;
import dataaccess.inmemory.LoginUserDetails;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

public class LogoutUseCaseTest {

    private static boolean logoutCalled = false;
    private static boolean success = false;

    private final static ILoginUserDetails loginUserDetails = new LoginUserDetails();
    private final static LogoutOutputBoundary logoutPresenter = new LogoutOutputBoundary() {

        @Override
        public void logoutSuccess() {
            success = true;
            logoutCalled = true;
        }

        @Override
        public void logoutFailed() {
            success = false;
            logoutCalled = true;
        }
    };
    private final static LogoutInteractor logoutInteractor = new LogoutInteractor(logoutPresenter, loginUserDetails);
    private final static LogoutController logoutController = new LogoutController(logoutInteractor);

    @Test
    public void testLogout() {
        loginUserDetails.login(1);
        logoutController.logout();

        await().until(() -> logoutCalled);

        assertTrue(success);
        assertEquals(0, loginUserDetails.getUserId());
    }
}
