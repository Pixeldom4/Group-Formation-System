package usecase.manageusers.edituser;

import org.junit.jupiter.api.Test;
import usecase.manageusers.edituser.EditUserInputData;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

/**
 * This class contains unit tests for the EditUserInputData class.
 */
public class EditUserInputDataTest {

    /**
     * Tests the constructor and the getter methods.
     */
    @Test
    public void testEditUserInputDataConstructorAndGetters() {
        int userId = 1;
        String firstName = "John";
        String lastName = "Doe";
        double desiredCompensation = 75000.0;
        HashSet<String> tags = new HashSet<>();
        tags.add("Developer");
        tags.add("Java");

        EditUserInputData inputData = new EditUserInputData(userId, firstName, lastName, desiredCompensation, tags);

        assertEquals(userId, inputData.getUserId());
        assertEquals(firstName, inputData.getFirstName());
        assertEquals(lastName, inputData.getLastName());
        assertEquals(desiredCompensation, inputData.getDesiredCompensation(), 0.001);
        assertEquals(tags, inputData.getTags());
    }

    /**
     * Tests that the getFirstName method returns the correct first name.
     */
    @Test
    public void testGetFirstName() {
        String firstName = "John";
        EditUserInputData inputData = new EditUserInputData(1, firstName, "Doe", 75000.0, new HashSet<>());
        assertEquals(firstName, inputData.getFirstName());
    }

    /**
     * Tests that the getLastName method returns the correct last name.
     */
    @Test
    public void testGetLastName() {
        String lastName = "Doe";
        EditUserInputData inputData = new EditUserInputData(1, "John", lastName, 75000.0, new HashSet<>());
        assertEquals(lastName, inputData.getLastName());
    }

    /**
     * Tests that the getDesiredCompensation method returns the correct desired compensation.
     */
    @Test
    public void testGetDesiredCompensation() {
        double desiredCompensation = 75000.0;
        EditUserInputData inputData = new EditUserInputData(1, "John", "Doe", desiredCompensation, new HashSet<>());
        assertEquals(desiredCompensation, inputData.getDesiredCompensation(), 0.001);
    }

    /**
     * Tests that the getUserId method returns the correct user ID.
     */
    @Test
    public void testGetUserId() {
        int userId = 1;
        EditUserInputData inputData = new EditUserInputData(userId, "John", "Doe", 75000.0, new HashSet<>());
        assertEquals(userId, inputData.getUserId());
    }

    /**
     * Tests that the getTags method returns the correct tags.
     */
    @Test
    public void testGetTags() {
        HashSet<String> tags = new HashSet<>();
        tags.add("Developer");
        tags.add("Java");
        EditUserInputData inputData = new EditUserInputData(1, "John", "Doe", 75000.0, tags);
        assertEquals(tags, inputData.getTags());
    }
}
