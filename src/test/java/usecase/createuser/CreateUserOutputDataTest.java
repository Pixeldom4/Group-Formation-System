package usecase.createuser;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the CreateUserOutputData class.
 */
class CreateUserOutputDataTest {

    /**
     * Tests the creation of CreateUserOutputData with valid input.
     */
    @Test
    void createUserOutputDataWithValidInput() {
        HashSet<String> tags = new HashSet<>();
        tags.add("developer");
        tags.add("java");

        CreateUserOutputData outputData = new CreateUserOutputData(1, "John", "Doe", "john.doe@test.com", 50000.0, tags, true);

        assertEquals(1, outputData.getUserId());
        assertEquals("John", outputData.getFirstName());
        assertEquals("Doe", outputData.getLastName());
        assertEquals("john.doe@test.com", outputData.getUserEmail());
        assertEquals(50000.0, outputData.getDesiredCompensation());
        assertTrue(outputData.getTags().containsAll(tags));
        assertTrue(outputData.isSuccess());
    }

    /**
     * Tests the creation of CreateUserOutputData with an empty first name.
     */
    @Test
    void createUserOutputDataWithEmptyFirstName() {
        HashSet<String> tags = new HashSet<>();
        tags.add("developer");
        tags.add("java");

        CreateUserOutputData outputData = new CreateUserOutputData(1, "", "Doe", "john.doe@test.com", 50000.0, tags, true);

        assertEquals("", outputData.getFirstName());
        assertEquals("Doe", outputData.getLastName());
    }

    /**
     * Tests the creation of CreateUserOutputData with an empty last name.
     */
    @Test
    void createUserOutputDataWithEmptyLastName() {
        HashSet<String> tags = new HashSet<>();
        tags.add("developer");
        tags.add("java");

        CreateUserOutputData outputData = new CreateUserOutputData(1, "John", "", "john.doe@test.com", 50000.0, tags, true);

        assertEquals("John", outputData.getFirstName());
        assertEquals("", outputData.getLastName());
    }

    /**
     * Tests the creation of CreateUserOutputData with an empty email.
     */
    @Test
    void createUserOutputDataWithEmptyEmail() {
        HashSet<String> tags = new HashSet<>();
        tags.add("developer");
        tags.add("java");

        CreateUserOutputData outputData = new CreateUserOutputData(1, "John", "Doe", "", 50000.0, tags, true);

        assertEquals("", outputData.getUserEmail());
    }

    /**
     * Tests the creation of CreateUserOutputData with empty tags.
     */
    @Test
    void createUserOutputDataWithEmptyTags() {
        HashSet<String> tags = new HashSet<>();

        CreateUserOutputData outputData = new CreateUserOutputData(1, "John", "Doe", "john.doe@test.com", 50000.0, tags, true);

        assertTrue(outputData.getTags().isEmpty());
    }

    /**
     * Tests the creation of CreateUserOutputData with null tags.
     */
    @Test
    void createUserOutputDataWithNullTags() {
        CreateUserOutputData outputData = new CreateUserOutputData(1, "John", "Doe", "john.doe@test.com", 50000.0, null, true);

        assertNull(outputData.getTags());
    }

    /**
     * Tests the creation of CreateUserOutputData with success set to false.
     */
    @Test
    void createUserOutputDataWithSuccessFalse() {
        HashSet<String> tags = new HashSet<>();
        tags.add("developer");
        tags.add("java");

        CreateUserOutputData outputData = new CreateUserOutputData(1, "John", "Doe", "john.doe@test.com", 50000.0, tags, false);

        assertFalse(outputData.isSuccess());
    }
}
