package dataaccess.database;

import dataaccess.database.manager.*;
import entities.Application;
import entities.Project;
import entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationRepositoryTest {
    private ApplicationRepository applicationRepository;
    private UserRepository userRepository;
    private ProjectRepository projectRepository;

    private int testUserId;
    private int testProjectId;
    private String testEmail = "testuser@test.com";

    @BeforeEach
    void setUp() {
        tearDown();
        String databaseName = "refactoredtest.db";

        // Initialize manager classes
        UserTagsManager userTagsManager = new UserTagsManager(databaseName);
        UserProjectsManager userProjectsManager = new UserProjectsManager(databaseName);
        UserManager userManager = new UserManager(databaseName);

        ProjectManager projectManager = new ProjectManager(databaseName);
        ProjectTagsManager projectTagsManager = new ProjectTagsManager(databaseName);
        ProjectEmbeddingsManager projectEmbeddingsManager = new ProjectEmbeddingsManager(databaseName);

        ApplicationManager applicationManager = new ApplicationManager(databaseName);

        // Create facade instances
        this.userRepository = new UserRepository(userManager, userTagsManager, userProjectsManager);
        this.projectRepository = new ProjectRepository(projectManager, projectTagsManager, projectEmbeddingsManager, userProjectsManager);
        this.applicationRepository = new ApplicationRepository(applicationManager);

        // Connect to the database
        userManager.connect();
        userProjectsManager.connect();
        userTagsManager.connect();
        projectManager.connect();
        projectTagsManager.connect();
        projectEmbeddingsManager.connect();
        applicationManager.connect();

        // Initialize the database tables
        userManager.initialize();
        userTagsManager.initialize();
        projectManager.initialize();
        projectTagsManager.initialize();
        projectEmbeddingsManager.initialize();
        userProjectsManager.initialize();
        applicationManager.initialize();

        // Clean up any existing data
        deleteUserByEmail(testEmail);

        // Create a test user
        HashSet<String> userTags = new HashSet<>();
        userTags.add("Developer");
        User user = userRepository.createUser(testEmail, "Test", "User", userTags, 50000.0, "password");
        testUserId = user.getUserId();

        // Create a test project
        HashSet<String> projectTags = new HashSet<>();
        projectTags.add("Java");
        projectTags.add("SQL");
        Project project = projectRepository.createProject("Test Project", 1000.0, "This is a test project", projectTags, new float[]{0.1f, 0.2f, 0.3f}, testUserId);
        testProjectId = project.getProjectId();
    }

    @AfterEach
    void tearDown() {
        if (userRepository != null) {
            deleteUserByEmail(testEmail);
        }
    }

    private void deleteUserByEmail(String email) {
        User user = userRepository.getUserByEmail(email);
        if (user != null) {
            userRepository.deleteUser(user.getUserId());
        }
    }

    @Test
    void createApplication() {
        byte[] pdfBytes = "Test PDF Content".getBytes();
        Application application = applicationRepository.createApplication(testUserId, testProjectId, "Test Application Text", pdfBytes);

        assertNotNull(application);
        assertEquals(testUserId, application.getSenderUserId());
        assertEquals(testProjectId, application.getProjectId());
        assertEquals("Test Application Text", application.getText());
        assertArrayEquals(pdfBytes, application.getPdfBytes());
    }

    @Test
    void getApplication() {
        byte[] pdfBytes = "Test PDF Content".getBytes();
        applicationRepository.createApplication(testUserId, testProjectId, "Test Application Text", pdfBytes);

        Application application = applicationRepository.getApplication(testUserId, testProjectId);

        assertNotNull(application);
        assertEquals(testUserId, application.getSenderUserId());
        assertEquals(testProjectId, application.getProjectId());
        assertEquals("Test Application Text", application.getText());
        assertArrayEquals(pdfBytes, application.getPdfBytes());
    }

    @Test
    void getApplicationsForUser() {
        byte[] pdfBytes = "Test PDF Content".getBytes();
        applicationRepository.createApplication(testUserId, testProjectId, "Test Application Text", pdfBytes);

        HashSet<Application> applications = applicationRepository.getApplicationsForUser(testUserId);

        assertNotNull(applications);
        assertEquals(1, applications.size());
        Application application = applications.iterator().next();
        assertEquals(testUserId, application.getSenderUserId());
        assertEquals(testProjectId, application.getProjectId());
        assertEquals("Test Application Text", application.getText());
        assertArrayEquals(pdfBytes, application.getPdfBytes());
    }

    @Test
    void getApplicationsForProject() {
        byte[] pdfBytes = "Test PDF Content".getBytes();
        applicationRepository.createApplication(testUserId, testProjectId, "Test Application Text", pdfBytes);

        HashSet<Application> applications = applicationRepository.getApplicationsForProject(testProjectId);

        assertNotNull(applications);
        assertEquals(1, applications.size());
        Application application = applications.iterator().next();
        assertEquals(testUserId, application.getSenderUserId());
        assertEquals(testProjectId, application.getProjectId());
        assertEquals("Test Application Text", application.getText());
        assertArrayEquals(pdfBytes, application.getPdfBytes());
    }

    @Test
    void deleteApplication() {
        byte[] pdfBytes = "Test PDF Content".getBytes();
        applicationRepository.createApplication(testUserId, testProjectId, "Test Application Text", pdfBytes);

        boolean deleted = applicationRepository.deleteApplication(testUserId, testProjectId);
        assertTrue(deleted);

        Application application = applicationRepository.getApplication(testUserId, testProjectId);
        assertNull(application);
    }
}
