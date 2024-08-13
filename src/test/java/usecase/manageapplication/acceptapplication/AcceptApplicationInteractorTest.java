package usecase.manageapplication.acceptapplication;

import dataaccess.IApplicationRepository;
import dataaccess.IUserProjectsRepository;
import dataaccess.IUserRepository;
import dataaccess.local.LocalApplicationRepository;
import dataaccess.local.LocalUserProjectsRepository;
import dataaccess.local.LocalUserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.manageapplications.ManageApplicationsController;
import usecase.manageapplications.acceptapplication.*;
import viewmodel.DisplayProjectApplicationViewModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SuppressWarnings("FieldCanBeLocal")
public class AcceptApplicationInteractorTest {

    private final static String SAVE_LOCATION = "local_data/test/usecase/acceptapplication/";
    private final static File userSaveFile = new File(SAVE_LOCATION + "users.csv");
    private final static File userProjectSaveFile = new File(SAVE_LOCATION + "userProjects.csv");
    private final static File applicationSaveFile = new File(SAVE_LOCATION + "applications.csv");
    private IApplicationRepository applicationRepository;
    private IUserProjectsRepository userProjectsRepository;
    private IUserRepository userRepository;
    private AcceptApplicationOutputBoundary acceptApplicationPresenter;
    private DisplayProjectApplicationViewModel viewModel;
    private AcceptApplicationInputBoundary interactor;
    private ManageApplicationsController controller;

    /**
     * Sets up the test environment before each test.
     * @throws IOException if an I/O error occurs when deleting files
     */
    @BeforeEach
    public void setUp() throws IOException {
        Files.deleteIfExists(userSaveFile.toPath());
        Files.deleteIfExists(userProjectSaveFile.toPath());
        Files.deleteIfExists(applicationSaveFile.toPath());

        applicationRepository = new LocalApplicationRepository(SAVE_LOCATION);
        userProjectsRepository = new LocalUserProjectsRepository(SAVE_LOCATION);
        userRepository = new LocalUserRepository(SAVE_LOCATION);
        viewModel = mock(DisplayProjectApplicationViewModel.class);
        acceptApplicationPresenter = new AcceptApplicationPresenter(viewModel);
        interactor = new AcceptApplicationInteractor(acceptApplicationPresenter, applicationRepository,
                                                     userProjectsRepository, userRepository);
        controller = new ManageApplicationsController(null, interactor, null);
    }

    /**
     * Tests the interactor's ability to accept an applicant.
     * The interactor is called through the controller.
     * The interactor should add the user to the project, delete the application, and prepare the success view.
     */
    @Test
    public void testAcceptApplicant() {
        userRepository.createUser("test@test.com", "first", "last",
                                  new HashSet<>(Arrays.asList("Java", "Programming")), 1234, "password");
        applicationRepository.createApplication(1, 10, "text", new byte[0]);

        controller.acceptApplicant(10, 1);
        verify(viewModel).setSenderName("first last");
        verify(viewModel).acceptedResult(true);

        assertIterableEquals(new HashSet<>(List.of(1)), userProjectsRepository.getUserIdsForProject(10));
        assertIterableEquals(new HashSet<>(List.of(10)), userProjectsRepository.getProjectIdsForUser(1));
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