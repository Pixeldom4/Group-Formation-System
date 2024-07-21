package dataaccess.database;

import entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the UserRepository class.
 */
class UserRepositoryTest {
    private UserRepository userRepository;
    private UserProjectsRepository userProjectsRepository;
    private int testUserId;
    private String testEmail = "testuser@test.com";

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        tearDown();
        String databaseName = "test12345.db";

        this.userProjectsRepository = new UserProjectsRepository(databaseName);
        this.userRepository = new UserRepository(databaseName, userProjectsRepository);

        this.userProjectsRepository.connect();
        this.userRepository.connect();

        this.userRepository.initialize();
        this.userProjectsRepository.initialize();

        // Clean up any existing user with the test email
        deleteUserByEmail(testEmail);

        // Create a test user
        HashSet<String> userTags = new HashSet<>();
        userTags.add("Developer");
        User user = userRepository.createUser(testEmail, "Test", "User", userTags, 50000.0, "password");
        testUserId = user.getUserId();
    }

    /**
     * Cleans up the test environment after each test.
     */
    @AfterEach
    void tearDown() {
        // Clean up any existing user with the test email
        if (userRepository != null) {
            deleteUserByEmail(testEmail);
        }

        if (userRepository != null) {
            userRepository.disconnect();
        }
        if (userProjectsRepository != null) {
            userProjectsRepository.disconnect();
        }
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
     * Tests the creation of a user.
     */
    @Test
    void createUser() {
        HashSet<String> tags = new HashSet<>();
        tags.add("Tester");

        // Use a different email for this test to ensure no conflict
        String newUserEmail = "newuser@test.com";
        deleteUserByEmail(newUserEmail);

        User user = userRepository.createUser(newUserEmail, "New", "User", tags, 60000.0, "newpassword");

        assertNotNull(user);
        assertEquals("New", user.getFirstName());
        assertEquals("User", user.getLastName());
        assertEquals(newUserEmail, user.getUserEmail());
        assertTrue(user.getTags().contains("Tester"));
    }

    /**
     * Tests the retrieval of a user by their email.
     */
    @Test
    void getUserByEmail() {
        User user = userRepository.getUserByEmail(testEmail);

        assertNotNull(user);
        assertEquals("Test", user.getFirstName());
        assertEquals("User", user.getLastName());
        assertEquals(50000.0, user.getDesiredCompensation(), 0);
    }

    /**
     * Tests the retrieval of a user by their ID.
     */
    @Test
    void getUserById() {
        User user = userRepository.getUserById(testUserId);

        assertNotNull(user);
        assertEquals("Test", user.getFirstName());
        assertEquals("User", user.getLastName());
        assertEquals(50000.0, user.getDesiredCompensation(), 0);
    }

    /**
     * Tests the deletion of a user.
     */
    @Test
    void deleteUser() {
        boolean deleted = userRepository.deleteUser(testUserId);
        assertTrue(deleted);

        User user = userRepository.getUserById(testUserId);
        assertNull(user);
    }

    /**
     * Tests adding tags to a user.
     */
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

    /**
     * Tests removing tags from a user.
     */
    @Test
    void removeTags() {
        HashSet<String> tagsToRemove = new HashSet<>();
        tagsToRemove.add("Developer");

        boolean result = userRepository.removeTags(testUserId, tagsToRemove);
        assertTrue(result);

        User user = userRepository.getUserById(testUserId);
        assertFalse(user.getTags().contains("Developer"));
    }

    /**
     * Tests updating a user's details.
     */
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

    // missing test for getPassswordByEmail for now, since that uses hashing. Will implement it later
}
