package usecase.deleteuser;

import dataaccess.IUserRepository;
import dataaccess.local.LocalUserRepository;
import org.junit.jupiter.api.Test;
import usecase.BCryptPasswordHasher;
import usecase.PasswordHasher;
import usecase.manageusers.deleteuser.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DeleteUserInteractorTest {
    private final static String SAVE_LOCATION = "local_data/test/usecase/deleteuser/";
    private static IUserRepository userRepository;
    private final static File saveFile = new File(SAVE_LOCATION + "users.csv");
    private static final PasswordHasher passwordHasher = new BCryptPasswordHasher();

    private static DeleteUserInputBoundary deleteUserInteractor;
    private static DeleteUserOutputBoundary deleteUserPresenter;
    private static DeleteUserController deleteUserController;

    @Test
    public void testDeleteUser() throws IOException {
        Files.deleteIfExists(saveFile.toPath());
        userRepository = new LocalUserRepository(SAVE_LOCATION);
        deleteUserPresenter = mock(DeleteUserPresenter.class);
        deleteUserInteractor = new DeleteUserInteractor(userRepository, (DeleteUserPresenter) deleteUserPresenter);
        deleteUserController = new DeleteUserController(deleteUserInteractor);
        userRepository.createUser("test@test.com",
                                  "first",
                                  "last",
                                  new HashSet<>(Arrays.asList("Java", "Programming")),
                                  1234.5,
                                  "password");

        deleteUserController.deleteUser(1);

        verify(deleteUserPresenter).prepareSuccessView(any(DeleteUserOutputData.class));
        assertNull(userRepository.getUserByEmail("test@test.com"));
    }


}
