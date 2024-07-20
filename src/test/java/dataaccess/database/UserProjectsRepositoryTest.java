package dataaccess.database;

import dataaccess.IUserProjectsRepository;
import dataaccess.IUserRepository;
import dataaccess.IProjectRepository;
import entities.Project;
import entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class UserProjectsRepositoryTest {
    private UserProjectsRepository userProjectsRepository;
    private UserRepository userRepository;
    private ProjectRepository projectRepository;
    private int testUserId;
    private int testProjectId;
    private String testEmail = "testuser@test.com";

    @BeforeEach
    void setUp() {
        tearDown();
        String databaseName = "test123.db";

        this.userProjectsRepository = new UserProjectsRepository(databaseName);
        this.userRepository = new UserRepository(databaseName, userProjectsRepository);
        this.projectRepository = new ProjectRepository(databaseName, userProjectsRepository);

        this.userProjectsRepository.connect();
        this.userRepository.connect();
        this.projectRepository.connect();

        this.userRepository.initialize();
        this.projectRepository.initialize();
        this.userProjectsRepository.initialize();

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
        // Clean up any existing data
        if (userRepository != null) {
            deleteUserByEmail(testEmail);
        }

        if (userRepository != null) {
            userRepository.disconnect();
        }
        if (projectRepository != null) {
            projectRepository.disconnect();
        }
        if (userProjectsRepository != null) {
            userProjectsRepository.disconnect();
        }
    }

    // Method to delete user by email using UserRepository methods
    private void deleteUserByEmail(String email) {
        User user = userRepository.getUserByEmail(email);
        if (user != null) {
            userRepository.deleteUser(user.getUserId());
        }
    }

    @Test
    void addUserToProject() {
        boolean result = userProjectsRepository.addUserToProject(testUserId, testProjectId);
        assertTrue(result);

        HashSet<Integer> projectIds = userProjectsRepository.getProjectIdsForUser(testUserId);
        assertTrue(projectIds.contains(testProjectId));
    }

    @Test
    void removeUserFromProject() {
        // First, add the user to the project
        userProjectsRepository.addUserToProject(testUserId, testProjectId);

        // Now, remove the user from the project
        boolean result = userProjectsRepository.removeUserFromProject(testUserId, testProjectId);
        assertTrue(result);

        HashSet<Integer> projectIds = userProjectsRepository.getProjectIdsForUser(testUserId);
        assertFalse(projectIds.contains(testProjectId));
    }

    @Test
    void removeUserFromAllProjects() {
        // First, add the user to the project
        userProjectsRepository.addUserToProject(testUserId, testProjectId);

        // Now, remove the user from all projects
        boolean result = userProjectsRepository.removeUserFromAllProjects(testUserId);
        assertTrue(result);

        HashSet<Integer> projectIds = userProjectsRepository.getProjectIdsForUser(testUserId);
        assertFalse(projectIds.contains(testProjectId));
    }

    @Test
    void removeProjectFromAllUsers() {
        // First, add the user to the project
        userProjectsRepository.addUserToProject(testUserId, testProjectId);

        // Now, remove the project from all users
        boolean result = userProjectsRepository.removeProjectFromAllUsers(testProjectId);
        assertTrue(result);

        HashSet<Integer> userIds = userProjectsRepository.getUserIdsForProject(testProjectId);
        assertFalse(userIds.contains(testUserId));
    }

    @Test
    void getProjectIdsForUser() {
        // First, add the user to the project
        userProjectsRepository.addUserToProject(testUserId, testProjectId);

        // Now, retrieve the project IDs for the user
        HashSet<Integer> projectIds = userProjectsRepository.getProjectIdsForUser(testUserId);

        assertNotNull(projectIds);
        assertTrue(projectIds.contains(testProjectId));
    }

    @Test
    void getUserIdsForProject() {
        // First, add the user to the project
        userProjectsRepository.addUserToProject(testUserId, testProjectId);

        // Now, retrieve the user IDs for the project
        HashSet<Integer> userIds = userProjectsRepository.getUserIdsForProject(testProjectId);

        assertNotNull(userIds);
        assertTrue(userIds.contains(testUserId));
    }
}
