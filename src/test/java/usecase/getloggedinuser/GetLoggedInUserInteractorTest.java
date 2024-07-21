package usecase.getloggedinuser;

import dataaccess.ILoginUserDetails;
import dataaccess.IUserRepository;
import dataaccess.inmemory.LoginUserDetails;
import dataaccess.local.LocalUserRepository;
import entities.User;
import entities.UserInterface;
import org.junit.jupiter.api.Test;
import usecase.BCryptPasswordHasher;
import usecase.PasswordHasher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the GetLoggedInUserInteractor class.
 */
public class GetLoggedInUserInteractorTest {
    private final static String SAVE_LOCATION = "local_data/test/usecase/getloggedinuser/";
    private static IUserRepository userRepository;
    private final static File saveFile = new File(SAVE_LOCATION + "users.csv");
    private static final PasswordHasher passwordHasher = new BCryptPasswordHasher();

    private static UserInterface outputUser;
    private static boolean interactorCalled = false;

    private final static ILoginUserDetails loginUserDetails = new LoginUserDetails();
    private final static LoggedInDataAccessViewModel loggedInDataAccessViewModel = new LoggedInDataAccessViewModel() {
        @Override
        public void setLoggedInUser(int userId, String firstName, String lastName, String userEmail, double desiredCompensation, HashSet<String> tags) {
            outputUser = new User(userId, firstName, lastName, userEmail, tags, desiredCompensation);
            interactorCalled = true;
        }

        @Override
        public void notLoggedIn() {
            outputUser = null;
            interactorCalled = true;
        }
    };
    private static GetLoggedInUserOutputBoundary getLoggedInUserOutputBoundary;
    private static GetLoggedInUserInteractor getLoggedInUserInteractor;
    private static GetLoggedInUserController getLoggedInUserController;

    /**
     * Tests the login details functionality.
     *
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void testLoginDetails() throws IOException {
        Files.deleteIfExists(saveFile.toPath());
        userRepository = new LocalUserRepository(SAVE_LOCATION);
        getLoggedInUserOutputBoundary = new GetLoggedInUserPresenter(loggedInDataAccessViewModel);
        getLoggedInUserInteractor = new GetLoggedInUserInteractor(getLoggedInUserOutputBoundary, loginUserDetails, userRepository);
        getLoggedInUserController = new GetLoggedInUserController(getLoggedInUserInteractor);
        userRepository.createUser("test@test.com",
                "first",
                "last",
                new HashSet<>(Arrays.asList("Java", "Programming")),
                1234.5,
                "password");
        loginUserDetails.login(1);
        getLoggedInUserController.getLoggedInUser();

        await().until(() -> interactorCalled);
        interactorCalled = false;

        assertEquals(outputUser.getUserId(), 1);
        assertEquals(outputUser.getFirstName(), "first");
        assertEquals(outputUser.getLastName(), "last");
        assertEquals(outputUser.getUserEmail(), "test@test.com");
        assertEquals(outputUser.getTags(), new HashSet<>(Arrays.asList("Java", "Programming")));
        assertEquals(outputUser.getDesiredCompensation(), 1234.5, 0.02);

        loginUserDetails.logout();
        getLoggedInUserController.getLoggedInUser();

        await().until(() -> interactorCalled);
        interactorCalled = false;

        assertNull(outputUser);
    }
}
