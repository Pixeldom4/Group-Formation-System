package dataaccess.database;

import dataaccess.database.manager.*;
import entities.Project;
import entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the UserProjectsRepository class.
 */
class UserProjectsRepositoryTest {
    private UserProjectsRepository userProjectsRepository;
    private UserRepository userRepository;
    private ProjectRepository projectRepository;

    private UserProjectsManager userProjectsManager;

    private int testUserId;
    private int testProjectId;
    private final String testEmail = "testuser@test.com";
    private final String testEmail2 = "testuser2@test.com";
    private int newUserId; // ID for the new test user

    private String databaseName;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        this.databaseName = "testing.db";

        UserTagsManager userTagsManager = new UserTagsManager(databaseName);
        this.userProjectsManager = new UserProjectsManager(databaseName);
        UserManager userManager = new UserManager(databaseName);

        ProjectManager projectManager = new ProjectManager(databaseName);
        ProjectTagsManager projectTagsManager = new ProjectTagsManager(databaseName);
        ProjectEmbeddingsManager projectEmbeddingsManager = new ProjectEmbeddingsManager(databaseName);

        // Create facade instances
        this.userRepository = new UserRepository(userManager, userTagsManager, userProjectsManager);
        this.projectRepository = new ProjectRepository(projectManager, projectTagsManager, projectEmbeddingsManager, userProjectsManager);
        this.userProjectsRepository = new UserProjectsRepository(userProjectsManager);

        userManager.connect();
        userTagsManager.connect();
        projectManager.connect();
        projectTagsManager.connect();
        projectEmbeddingsManager.connect();
        userProjectsManager.connect();

        // Initialize the database tables
        userManager.initialize();
        userTagsManager.initialize();
        userProjectsManager.initialize();
        projectManager.initialize();
        projectTagsManager.initialize();
        projectEmbeddingsManager.initialize();

        // Clean up any existing data
        deleteUserByEmail(testEmail);
        deleteUserByEmail(testEmail2);

        // Create a test user
        HashSet<String> userTags = new HashSet<>();
        userTags.add("Developer");
        User user = userRepository.createUser(testEmail, "Test", "User", userTags, 50000.0, "password");
        testUserId = user.getUserId();

        // Create another test user
        HashSet<String> userTags2 = new HashSet<>();
        userTags2.add("Tester");
        User newUser = userRepository.createUser(testEmail2, "New", "User", userTags2, 60000.0, "password");
        newUserId = newUser.getUserId();

        // Create a test project
        HashSet<String> projectTags = new HashSet<>();
        projectTags.add("Java");
        projectTags.add("SQL");
        Project project = projectRepository.createProject("Test Project", 1000.0, "This is a test project", projectTags, new float[]{0.1f, 0.2f, 0.3f}, testUserId);
        testProjectId = project.getProjectId();
    }

    /**
     * Cleans up the test environment after each test.
     */
    @AfterEach
    void tearDown() {
        // Clean up any existing data
        if (userRepository != null) {
            deleteUserByEmail(testEmail);
            deleteUserByEmail(testEmail2);
        }

        DatabaseConnection.disconnect();
        DatabaseHelper.deleteDatabaseFile(databaseName);
    }

    /**
     * Deletes a user by email using UserRepository methods.
     *
     * @param email the email of the user to delete
     */
    private void deleteUserByEmail(String email) {
        User user = userRepository.getUserByEmail(email);
        if (user != null) {
            userRepository.deleteUser(user.getUserId());
        }
    }

    /**
     * Tests adding a user to a project.
     */
    @Test
    void addUserToProject() {
        // Add the new user to the project
        boolean result = userProjectsRepository.addUserToProject(newUserId, testProjectId);
        assertTrue(result);

        HashSet<Integer> projectIds = userProjectsRepository.getProjectIdsForUser(newUserId);
        assertTrue(projectIds.contains(testProjectId));
    }

    /**
     * Tests removing a user from a project.
     */
    @Test
    void removeUserFromProject() {
        // First, add the user to the project
        userProjectsRepository.addUserToProject(newUserId, testProjectId);

        // Now, remove the user from the project
        boolean result = userProjectsRepository.removeUserFromProject(newUserId, testProjectId);
        assertTrue(result);

        HashSet<Integer> projectIds = userProjectsRepository.getProjectIdsForUser(newUserId);
        assertFalse(projectIds.contains(testProjectId));
    }

    /**
     * Tests removing a user from all projects.
     */
    @Test
    void removeUserFromAllProjects() {
        // First, add the user to the project
        userProjectsRepository.addUserToProject(newUserId, testProjectId);

        // Now, remove the user from all projects
        boolean result = userProjectsRepository.removeUserFromAllProjects(newUserId);
        assertTrue(result);

        HashSet<Integer> projectIds = userProjectsRepository.getProjectIdsForUser(newUserId);
        assertFalse(projectIds.contains(testProjectId));
    }

    /**
     * Tests removing a project from all users.
     */
    @Test
    void removeProjectFromAllUsers() {
        // First, add the user to the project
        userProjectsRepository.addUserToProject(newUserId, testProjectId);

        // Now, remove the project from all users
        boolean result = userProjectsRepository.removeProjectFromAllUsers(testProjectId);
        assertTrue(result);

        HashSet<Integer> userIds = userProjectsRepository.getUserIdsForProject(testProjectId);
        assertFalse(userIds.contains(newUserId));
    }

    /**
     * Tests the retrieval of project IDs for a specific user.
     */
    @Test
    void getProjectIdsForUser() {
        // First, add the user to the project
        userProjectsRepository.addUserToProject(newUserId, testProjectId);

        // Now, retrieve the project IDs for the user
        HashSet<Integer> projectIds = userProjectsRepository.getProjectIdsForUser(newUserId);

        assertNotNull(projectIds);
        assertTrue(projectIds.contains(testProjectId));
    }

    /**
     * Tests the retrieval of user IDs for a specific project.
     */
    @Test
    void getUserIdsForProject() {
        // First, add the user to the project
        userProjectsRepository.addUserToProject(newUserId, testProjectId);

        // Now, retrieve the user IDs for the project
        HashSet<Integer> userIds = userProjectsRepository.getUserIdsForProject(testProjectId);

        assertNotNull(userIds);
        assertTrue(userIds.contains(newUserId));
    }
}
