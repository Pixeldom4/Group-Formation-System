package entities;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * Unit tests for the User class.
 */
public class UserTest {

    private int userId;
    private String firstName;
    private String lastName;
    private String userEmail;
    private HashSet<String> tags;
    private double desiredCompensation;
    private User user;

    /**
     * Sets up the test environment before each test.
     */
    @Before
    public void setUp() {
        userId = 1;
        firstName = "John";
        lastName = "Doe";
        userEmail = "john.doe@example.com";
        tags = new HashSet<>();
        tags.add("Developer");
        tags.add("Java");
        desiredCompensation = 50000.0;
        user = new User(userId, firstName, lastName, userEmail, tags, desiredCompensation);
    }

    /**
     * Tests the constructor of the User class.
     */
    @Test
    public void testConstructor() {
        assertNotNull("The user object should not be null", user);
        assertEquals("The user ID should match", userId, user.getUserId());
        assertEquals("The first name should match", firstName, user.getFirstName());
        assertEquals("The last name should match", lastName, user.getLastName());
        assertEquals("The user email should match", userEmail, user.getUserEmail());
        assertEquals("The tags should match", tags, user.getTags());
        assertEquals("The desired compensation should match", desiredCompensation, user.getDesiredCompensation(), 0.0);
    }

    /**
     * Tests the getUserId and setUserId methods of the User class.
     */
    @Test
    public void testGetAndSetUserId() {
        int newUserId = 2;
        user.setUserId(newUserId);

        assertEquals("The new user ID should match", newUserId, user.getUserId());
    }

    /**
     * Tests the getFirstName and setFirstName methods of the User class.
     */
    @Test
    public void testGetAndSetFirstName() {
        String newFirstName = "Jane";
        user.setFirstName(newFirstName);

        assertEquals("The new first name should match", newFirstName, user.getFirstName());
    }

    /**
     * Tests the getLastName and setLastName methods of the User class.
     */
    @Test
    public void testGetAndSetLastName() {
        String newLastName = "Smith";
        user.setLastName(newLastName);

        assertEquals("The new last name should match", newLastName, user.getLastName());
    }

    /**
     * Tests the getUserEmail and setUserEmail methods of the User class.
     */
    @Test
    public void testGetAndSetUserEmail() {
        String newUserEmail = "jane.smith@example.com";
        user.setUserEmail(newUserEmail);

        assertEquals("The new user email should match", newUserEmail, user.getUserEmail());
    }

    /**
     * Tests the getTags and setTags methods of the User class.
     */
    @Test
    public void testGetAndSetTags() {
        HashSet<String> newTags = new HashSet<>();
        newTags.add("Manager");
        newTags.add("Team Lead");
        user.setTags(newTags);

        assertEquals("The new tags should match", newTags, user.getTags());
    }

    /**
     * Tests the getDesiredCompensation and setDesiredCompensation methods of the User class.
     */
    @Test
    public void testGetAndSetDesiredCompensation() {
        double newDesiredCompensation = 60000.0;
        user.setDesiredCompensation(newDesiredCompensation);

        assertEquals("The new desired compensation should match", newDesiredCompensation, user.getDesiredCompensation(), 0.0);
    }
}
