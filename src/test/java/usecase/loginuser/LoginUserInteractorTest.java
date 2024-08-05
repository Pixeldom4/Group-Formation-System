package usecase.loginuser;

import dataaccess.ILoginUserDetails;
import dataaccess.IUserRepository;
import dataaccess.inmemory.LoginUserDetails;
import dataaccess.local.LocalUserRepository;
import org.junit.jupiter.api.Test;
import usecase.BCryptPasswordHasher;
import usecase.PasswordHasher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SuppressWarnings("FieldCanBeLocal")
public class LoginUserInteractorTest {
    private final static String SAVE_LOCATION = "local_data/test/usecase/loginuser/";
    private static IUserRepository userRepository;
    private final static File saveFile = new File(SAVE_LOCATION + "users.csv");
    private static final PasswordHasher passwordHasher = new BCryptPasswordHasher();

    private static LoginUserInputBoundary loginUserInteractor;
    private static LoginUserOutputBoundary loginUserPresenter;
    private static LoginAuthenticator loginAuthenticator;
    private static ILoginUserDetails loginUserDetails;
    private static LoginUserController loginUserController;

    @Test
    public void testLoginUser() throws IOException {
        Files.deleteIfExists(saveFile.toPath());
        userRepository = new LocalUserRepository(SAVE_LOCATION);

        loginUserPresenter = mock(LoginUserPresenter.class);
        loginAuthenticator = new LoginAuthenticatorService(userRepository, passwordHasher);
        loginUserDetails = new LoginUserDetails();
        loginUserInteractor = new LoginUserInteractor(userRepository, loginUserPresenter, loginAuthenticator, loginUserDetails);
        loginUserController = new LoginUserController(loginUserInteractor);

        String hashedPassword = passwordHasher.hashPassword("password");
        userRepository.createUser("test@test.com",
                                  "first",
                                  "last",
                                  new HashSet<>(Arrays.asList("Java", "Programming")),
                                  1234.5,
                                  hashedPassword);

        loginUserController.loginUser("test@test.com", "abcdefg");

        verify(loginUserPresenter).prepareFailView(any(String.class));

        loginUserController.loginUser("test@test.com", "password");

        verify(loginUserPresenter).prepareSuccessView(any(LoginUserOutputData.class));
        int userId = loginUserDetails.getUserId();
        assertEquals("test@test.com", userRepository.getUserById(userId).getUserEmail());
        assertEquals("first", userRepository.getUserById(userId).getFirstName());
        assertEquals("last", userRepository.getUserById(userId).getLastName());
        assertEquals(1234.5, userRepository.getUserById(userId).getDesiredCompensation());
    }
}

