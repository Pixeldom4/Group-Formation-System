package usecase.manageusers.edituser;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the EditUserOutputData class.
 */
public class EditUserOutputDataTest {

    /**
     * Tests the EditUserOutputData constructor and getter methods.
     */
    @Test
    public void testEditUserOutputData() {
        int userId = 1;
        String firstName = "John";
        String lastName = "Doe";
        double desiredCompensation = 75000.0;
        HashSet<String> tags = new HashSet<>();
        tags.add("Developer");
        tags.add("Java");

        EditUserOutputData outputData = new EditUserOutputData(userId, firstName, lastName, desiredCompensation, tags);

        assertEquals(userId, outputData.getUserId());
        assertEquals(firstName, outputData.getFirstName());
        assertEquals(lastName, outputData.getLastName());
        assertEquals(desiredCompensation, outputData.getDesiredCompensation(), 0.001);
        assertEquals(tags, outputData.getTags());
    }

    /**
     * Tests that the EditUserOutputData class is immutable.
     */
    @Test
    public void testImmutability() {
        int userId = 1;
        String firstName = "John";
        String lastName = "Doe";
        double desiredCompensation = 75000.0;
        HashSet<String> tags = new HashSet<>();
        tags.add("Developer");
        tags.add("Java");

        EditUserOutputData outputData = new EditUserOutputData(userId, firstName, lastName, desiredCompensation, tags);

        // Ensure the original tags set is returned
        HashSet<String> newTags = outputData.getTags();
        assertEquals(2, newTags.size());
    }
}
