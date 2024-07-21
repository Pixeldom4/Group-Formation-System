package usecase.create_user_use_case;

import dataaccess.IUserRepository;
import dataaccess.local.LocalUserRepository;
import entities.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import usecase.PasswordHasher;
import usecase.createuser.CreateUserInputData;
import usecase.createuser.CreateUserInteractor;
import usecase.createuser.CreateUserOutputBoundary;
import usecase.createuser.CreateUserOutputData;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the CreateUserInteractor class.
 */
public class CreateUserInteractorTest {

    /**
     * The location where test data is saved.
     */
    private final static String SAVE_LOCATION = "local_data/test/create_user_interactor/";

    /**
     * A mock implementation of CreateUserOutputBoundary for testing.
     */
    private final static CreateUserOutputBoundary userPresenter = new CreateUserOutputBoundary() {
        @Override
        public void prepareSuccessView(CreateUserOutputData outputData) {
            assertNotNull(outputData);
            assertTrue(outputData.isSuccess());
            System.out.println("Success: " + outputData.getUserEmail());
        }

        @Override
        public void prepareFailView(String error) {
            fail("Create user failed: " + error);
        }
    };

    /**
     * A mock implementation of IUserRepository for testing.
     */
    private final static IUserRepository userRepository = new LocalUserRepository(SAVE_LOCATION);

    /**
     * A mock implementation of PasswordHasher for testing.
     */
    private final static PasswordHasher passwordHasher = new PasswordHasher() {
        @Override
        public String hashPassword(String password) {
            return "hashed" + password;
        }

        @Override
        public boolean checkPassword(String password, String hashedPassword) {
            return hashedPassword.equals("hashed" + password);
        }
    };

    /**
     * The CreateUserInteractor instance used for testing.
     */
    private final static CreateUserInteractor createUserInteractor = new CreateUserInteractor(userRepository, userPresenter, passwordHasher);

    /**
     * Sets up the test environment before all tests.
     */
    @BeforeAll
    public static void setUp() {}

    /**
     * Tests the creation of a user with valid input.
     */
    @Test
    public void createUserWithValidInput() {
        CreateUserInputData inputData = new CreateUserInputData("John", "Doe", "john.doe@test.com", 50000.0, new HashSet<>(Arrays.asList("developer", "java")), "password123");
        createUserInteractor.createUser(inputData);

        User createdUser = userRepository.getUserByEmail("john.doe@test.com");
        assertNotNull(createdUser);
        assertEquals("John", createdUser.getFirstName());
        assertEquals("Doe", createdUser.getLastName());
        assertEquals("john.doe@test.com", createdUser.getUserEmail());
        assertEquals(50000.0, createdUser.getDesiredCompensation());
        assertTrue(createdUser.getTags().containsAll(Arrays.asList("developer", "java")));
    }

    /**
     * Tests the creation of a user with an existing email.
     */
    @Test
    public void createUserWithExistingEmail() {
        CreateUserInputData inputData = new CreateUserInputData("John", "Doe", "john.doe@test.com", 50000.0, new HashSet<>(Arrays.asList("developer", "java")), "password123");
        createUserInteractor.createUser(inputData);

        CreateUserInputData duplicateInputData = new CreateUserInputData("Jane", "Smith", "john.doe@test.com", 60000.0, new HashSet<>(Arrays.asList("developer", "python")), "password456");
        createUserInteractor.createUser(duplicateInputData);

        User createdUser = userRepository.getUserByEmail("john.doe@test.com");
        assertNotNull(createdUser);
        assertEquals("John", createdUser.getFirstName());
        assertEquals("Doe", createdUser.getLastName());
        assertEquals(50000.0, createdUser.getDesiredCompensation());
    }

    /**
     * Tests the creation of a user with null user data.
     */
    @Test
    public void createUserWithNullUser() {
        CreateUserInputData inputData = new CreateUserInputData("John", "Doe", "john.doe@test.com", 50000.0, new HashSet<>(Arrays.asList("developer", "java")), "password123");
        createUserInteractor.createUser(inputData);

        User createdUser = userRepository.getUserByEmail("john.doe@test.com");
        assertNotNull(createdUser);
        assertEquals("John", createdUser.getFirstName());
        assertEquals("Doe", createdUser.getLastName());
        assertEquals("john.doe@test.com", createdUser.getUserEmail());
        assertEquals(50000.0, createdUser.getDesiredCompensation());
        assertTrue(createdUser.getTags().containsAll(Arrays.asList("developer", "java")));
    }

    /**
     * Tests the creation of a user with empty input data.
     */
    @Test
    public void createUserWithEmptyInput() {
        CreateUserInputData inputData = new CreateUserInputData("", "", "", 0.0, new HashSet<>(), "");
        createUserInteractor.createUser(inputData);

        User createdUser = userRepository.getUserByEmail("");
        assertNull(createdUser);
    }

}
