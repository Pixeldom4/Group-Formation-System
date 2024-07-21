package usecase.create_user_use_case;

import usecase.createuser.CreateUserInputData;

import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the CreateUserInputData class.
 */
class CreateUserInputDataTest {

    /**
     * Tests the creation of CreateUserInputData with valid input.
     */
    @Test
    void createUserInputDataWithValidInput() {
        HashSet<String> tags = new HashSet<>();
        tags.add("developer");
        tags.add("java");

        CreateUserInputData inputData = new CreateUserInputData("John", "Doe", "john.doe@test.com", 50000.0, tags, "password123");

        assertEquals("John", inputData.getFirstName());
        assertEquals("Doe", inputData.getLastName());
        assertEquals("john.doe@test.com", inputData.getEmail());
        assertEquals(50000.0, inputData.getDesiredCompensation());
        assertTrue(inputData.getTags().containsAll(tags));
        assertEquals("password123", inputData.getPassword());
    }

    /**
     * Tests the creation of CreateUserInputData with an empty first name.
     */
    @Test
    void createUserInputDataWithEmptyFirstName() {
        HashSet<String> tags = new HashSet<>();
        tags.add("developer");
        tags.add("java");

        CreateUserInputData inputData = new CreateUserInputData("", "Doe", "john.doe@test.com", 50000.0, tags, "password123");

        assertEquals("", inputData.getFirstName());
        assertEquals("Doe", inputData.getLastName());
    }

    /**
     * Tests the creation of CreateUserInputData with an empty last name.
     */
    @Test
    void createUserInputDataWithEmptyLastName() {
        HashSet<String> tags = new HashSet<>();
        tags.add("developer");
        tags.add("java");

        CreateUserInputData inputData = new CreateUserInputData("John", "", "john.doe@test.com", 50000.0, tags, "password123");

        assertEquals("John", inputData.getFirstName());
        assertEquals("", inputData.getLastName());
    }

    /**
     * Tests the creation of CreateUserInputData with an empty email.
     */
    @Test
    void createUserInputDataWithEmptyEmail() {
        HashSet<String> tags = new HashSet<>();
        tags.add("developer");
        tags.add("java");

        CreateUserInputData inputData = new CreateUserInputData("John", "Doe", "", 50000.0, tags, "password123");

        assertEquals("", inputData.getEmail());
    }

    /**
     * Tests the creation of CreateUserInputData with an empty password.
     */
    @Test
    void createUserInputDataWithEmptyPassword() {
        HashSet<String> tags = new HashSet<>();
        tags.add("developer");
        tags.add("java");

        CreateUserInputData inputData = new CreateUserInputData("John", "Doe", "john.doe@test.com", 50000.0, tags, "");

        assertEquals("", inputData.getPassword());
    }

    /**
     * Tests the creation of CreateUserInputData with empty tags.
     */
    @Test
    void createUserInputDataWithEmptyTags() {
        HashSet<String> tags = new HashSet<>();

        CreateUserInputData inputData = new CreateUserInputData("John", "Doe", "john.doe@test.com", 50000.0, tags, "password123");

        assertTrue(inputData.getTags().isEmpty());
    }

    /**
     * Tests the creation of CreateUserInputData with null tags.
     */
    @Test
    void createUserInputDataWithNullTags() {
        CreateUserInputData inputData = new CreateUserInputData("John", "Doe", "john.doe@test.com", 50000.0, null, "password123");

        assertNull(inputData.getTags());
    }
}