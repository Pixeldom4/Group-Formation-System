package usecase.rejectapplication;

import dataaccess.IApplicationRepository;
import dataaccess.IUserRepository;
import dataaccess.local.LocalApplicationRepository;
import dataaccess.local.LocalUserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import viewmodel.DisplayProjectApplicationViewModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RejectApplicationInteractorTest {

    private final static String SAVE_LOCATION = "local_data/test/usecase/rejectApplication/";
    private final static File userSaveFile = new File(SAVE_LOCATION + "users.csv");
    private final static File applicationSaveFile = new File(SAVE_LOCATION + "applications.csv");
    private IApplicationRepository applicationRepository;
    private IUserRepository userRepository;
    private RejectApplicationOutputBoundary rejectApplicationPresenter;
    private DisplayProjectApplicationViewModel viewModel;
    private RejectApplicationInputBoundary interactor;
    private RejectApplicationController controller;

    /**
     * Sets up the test environment before each test.
     * @throws IOException if an I/O error occurs when deleting files
     */
    @BeforeEach
    public void setUp() throws IOException {
        Files.deleteIfExists(userSaveFile.toPath());
        Files.deleteIfExists(applicationSaveFile.toPath());

        applicationRepository = new LocalApplicationRepository(SAVE_LOCATION);
        userRepository = new LocalUserRepository(SAVE_LOCATION);
        viewModel = mock(DisplayProjectApplicationViewModel.class);
        rejectApplicationPresenter = new RejectApplicationPresenter(viewModel);
        interactor = new RejectApplicationInteractor(rejectApplicationPresenter, applicationRepository, userRepository);
        controller = new RejectApplicationController(interactor);
    }

    /**
     * Tests the interactor's ability to reject an applicant.
     * The interactor is called through the controller.
     * The interactor should delete the application and prepare the success view.
     */
    @Test
    public void testAcceptApplicant() {
        userRepository.createUser("test@test.com", "first", "last",
                                  new HashSet<>(Arrays.asList("Java", "Programming")), 1234, "password");
        applicationRepository.createApplication(1, 10, "text", new byte[0]);

        controller.rejectApplicant(10, 1);
        verify(viewModel).setSenderName("first last");
        verify(viewModel).rejectedResult(true);

        assertNull(applicationRepository.getApplication(1, 10));

    }

    /**
     * Cleans up the test environment after all tests have been run to prevent side effects.
     * @throws IOException if an I/O error occurs when deleting files
     */
    @AfterAll
    public static void cleanUpFile() throws IOException {
        File folder = new File(SAVE_LOCATION);
        String[] files = folder.list();

        if (files == null) {
            return;
        }
        for (String file : files) {
            Files.deleteIfExists(new File(folder.getPath(), file).toPath());
        }
        folder.delete();
    }

}