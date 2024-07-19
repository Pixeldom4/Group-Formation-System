package usecase.getloggedinuser;

import dataaccess.ILoginUserDetails;
import dataaccess.inmemory.LoginUserDetails;
import entities.User;
import entities.UserInterface;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

public class GetLoggedInUserInteractorTest {

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
    private final static GetLoggedInUserOutputBoundary getLoggedInUserOutputBoundary = new GetLoggedInUserPresenter(loggedInDataAccessViewModel);
    private final static GetLoggedInUserInteractor getLoggedInUserInteractor = new GetLoggedInUserInteractor(getLoggedInUserOutputBoundary, loginUserDetails);
    private final static GetLoggedInUserController getLoggedInUserController = new GetLoggedInUserController(getLoggedInUserInteractor);


    @Test
    public void testLoginDetails(){
        loginUserDetails.login(1,
                               "test@test.com",
                               "first",
                               "last",
                               1234.5,
                               new HashSet<>(Arrays.asList("Java", "Programming")));
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
