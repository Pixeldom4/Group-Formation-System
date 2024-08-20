package dataaccess.database;

import dataaccess.database.manager.*;
import entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    private UserRepository userRepository;
    private UserProjectsManager userProjectsManager;
    private int testUserId;
    private String testEmail = "testuser@test.com";

    private String databaseName;

    @BeforeEach
    void setUp() {
        this.databaseName = "testing.db";

        UserTagsManager userTagsManager = new UserTagsManager(databaseName);
        userProjectsManager = new UserProjectsManager(databaseName);
        UserManager userManager = new UserManager(databaseName);

        ProjectManager projectManager = new ProjectManager(databaseName);
        ProjectTagsManager projectTagsManager = new ProjectTagsManager(databaseName);
        ProjectEmbeddingsManager projectEmbeddingsManager = new ProjectEmbeddingsManager(databaseName);

        // Create facade instances
        userRepository = new UserRepository(userManager, userTagsManager, userProjectsManager);
        ProjectRepository projectRepository = new ProjectRepository(projectManager, projectTagsManager, projectEmbeddingsManager, userProjectsManager);

        // Connect to the database
        userManager.connect();
        userProjectsManager.connect();
        userTagsManager.connect();
        projectManager.connect();
        projectTagsManager.connect();
        projectEmbeddingsManager.connect();

        // Initialize the database tables
        userManager.initialize();
        userTagsManager.initialize();
        userProjectsManager.initialize();
        projectManager.initialize();
        projectTagsManager.initialize();
        projectEmbeddingsManager.initialize();

        // Clean up any existing user with the test email
        deleteUserByEmail(testEmail);

        // Create a test user
        HashSet<String> userTags = new HashSet<>();
        userTags.add("Developer");
        if (userRepository.getUserByEmail(testEmail) != null) {
            System.out.println("User already exists\n\n");
        }
        User user = userRepository.createUser(testEmail, "Test", "User", userTags, 50000.0, "password");
        testUserId = user.getUserId();
    }

    @AfterEach
    void tearDown() {
        if (userRepository != null) {
            deleteUserByEmail(testEmail);
        }

        DatabaseConnection.disconnect();
        DatabaseHelper.deleteDatabaseFile(databaseName);
    }

    private void deleteUserByEmail(String email) {
        User user = userRepository.getUserByEmail(email);
        if (user != null) {
            userRepository.deleteUser(user.getUserId());
        }
    }

    @Test
    void createUser() {
        HashSet<String> tags = new HashSet<>();
        tags.add("Tester");

        String newUserEmail = "newuser@test.com";
        deleteUserByEmail(newUserEmail);

        User user = userRepository.createUser(newUserEmail, "New", "User", tags, 60000.0, "newpassword");

        assertNotNull(user);
        assertEquals("New", user.getFirstName());
        assertEquals("User", user.getLastName());
        assertEquals(newUserEmail, user.getUserEmail());
        assertTrue(user.getTags().contains("Tester"));
    }

    @Test
    void getUserByEmail() {
        User user = userRepository.getUserByEmail(testEmail);

        assertNotNull(user);
        assertEquals("Test", user.getFirstName());
        assertEquals("User", user.getLastName());
        assertEquals(50000.0, user.getDesiredCompensation(), 0);
    }

    @Test
    void getUserById() {
        User user = userRepository.getUserById(testUserId);

        assertNotNull(user);
        assertEquals("Test", user.getFirstName());
        assertEquals("User", user.getLastName());
        assertEquals(50000.0, user.getDesiredCompensation(), 0);
    }

    @Test
    void deleteUser() {
        boolean deleted = userRepository.deleteUser(testUserId);
        assertTrue(deleted);

        User user = userRepository.getUserById(testUserId);
        assertNull(user);
    }

    @Test
    void addTags() {
        HashSet<String> newTags = new HashSet<>();
        newTags.add("NewTag1");
        newTags.add("NewTag2");

        boolean result = userRepository.addTags(testUserId, newTags);
        assertTrue(result);

        User user = userRepository.getUserById(testUserId);
        assertTrue(user.getTags().contains("NewTag1"));
        assertTrue(user.getTags().contains("NewTag2"));
    }

    @Test
    void removeTags() {
        HashSet<String> tagsToRemove = new HashSet<>();
        tagsToRemove.add("Developer");

        boolean result = userRepository.removeTags(testUserId, tagsToRemove);
        assertTrue(result);

        User user = userRepository.getUserById(testUserId);
        assertFalse(user.getTags().contains("Developer"));
    }

    @Test
    void updateUser() {
        HashSet<String> newTags = new HashSet<>();
        newTags.add("UpdatedTag");

        boolean result = userRepository.updateUser(testUserId, "Updated", "User", 70000.0, newTags);
        assertTrue(result);

        User updatedUser = userRepository.getUserById(testUserId);
        assertNotNull(updatedUser);
        assertEquals("Updated", updatedUser.getFirstName());
        assertEquals("User", updatedUser.getLastName());
        assertEquals(70000.0, updatedUser.getDesiredCompensation(), 0);
        assertTrue(updatedUser.getTags().contains("UpdatedTag"));
    }
}
