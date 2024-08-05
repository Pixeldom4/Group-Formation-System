package usecase.createuser;

import dataaccess.IUserRepository;
import dataaccess.local.LocalUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import entities.User;
import usecase.PasswordHasher;
import usecase.manageusers.createuser.CreateUserController;
import usecase.manageusers.createuser.CreateUserInteractor;
import usecase.manageusers.createuser.CreateUserOutputBoundary;
import usecase.manageusers.createuser.CreateUserOutputData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the CreateUserController class.
 */
class CreateUserControllerTest {

    private CreateUserController controller;
    private CreateUserOutputBoundary userPresenter;
    private IUserRepository userRepository;
    private PasswordHasher passwordHasher;
    private CreateUserInteractor createUserInteractor;

    private final static String SAVE_LOCATION = "local_data/test/create_user_interactor/";
    private final static File saveFile = new File(SAVE_LOCATION + "users.csv");

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() throws IOException {
        userPresenter = new CreateUserOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateUserOutputData outputData) {
                assertNotNull(outputData);
                assertTrue(outputData.isSuccess());
            }

            @Override
            public void prepareFailView(String error) {
                assertNotNull(error);
            }
        };
        Files.deleteIfExists(saveFile.toPath());
        userRepository = new LocalUserRepository(SAVE_LOCATION);
        userRepository = new LocalUserRepository("local_data/test/create_user_interactor/");
        passwordHasher = new PasswordHasher() {
            @Override
            public String hashPassword(String password) {
                return "hashed" + password;
            }

            @Override
            public boolean checkPassword(String password, String hashedPassword) {
                return hashedPassword.equals("hashed" + password);
            }
        };
        createUserInteractor = new CreateUserInteractor(userRepository, userPresenter, passwordHasher);
        controller = new CreateUserController(createUserInteractor);
    }

    /**
     * Tests the creation of a user with valid input.
     */
    @Test
    void createUserWithValidInput() {
        HashSet<String> tags = new HashSet<>();
        tags.add("developer");
        tags.add("java");

        controller.createUser("John", "Doe", "john.doe@test.com", 50000.0, tags, "password123");

        User createdUser = userRepository.getUserByEmail("john.doe@test.com");
        assertNotNull(createdUser);
        assertEquals("John", createdUser.getFirstName());
        assertEquals("Doe", createdUser.getLastName());
        assertEquals("john.doe@test.com", createdUser.getUserEmail());
        assertEquals(50000.0, createdUser.getDesiredCompensation());
        assertTrue(createdUser.getTags().containsAll(tags));
    }

    /**
     * Tests the creation of a user with an empty first name.
     */
    @Test
    void createUserWithEmptyFirstName() {
        HashSet<String> tags = new HashSet<>();
        tags.add("developer");
        tags.add("java");

        controller.createUser("", "Doe", "john.doe@test.com", 50000.0, tags, "password123");

        User createdUser = userRepository.getUserByEmail("john.doe@test.com");
        assertNotNull(createdUser);
        assertEquals("", createdUser.getFirstName());
        assertEquals("Doe", createdUser.getLastName());
    }

    /**
     * Tests the creation of a user with an empty last name.
     */
    @Test
    void createUserWithEmptyLastName() {
        HashSet<String> tags = new HashSet<>();
        tags.add("developer");
        tags.add("java");

        controller.createUser("John", "", "john.doe@test.com", 50000.0, tags, "password123");

        User createdUser = userRepository.getUserByEmail("john.doe@test.com");
        assertNotNull(createdUser);
        assertEquals("John", createdUser.getFirstName());
        assertEquals("", createdUser.getLastName());
    }

    /**
     * Tests the creation of a user with an empty email.
     */
    @Test
    void createUserWithEmptyEmail() {
        HashSet<String> tags = new HashSet<>();
        tags.add("developer");
        tags.add("java");

        controller.createUser("John", "Doe", "", 50000.0, tags, "password123");

        User createdUser = userRepository.getUserByEmail("");
        assertNull(createdUser);
    }

    /**
     * Tests the creation of a user with an empty password.
     */
    @Test
    void createUserWithEmptyPassword() {
        HashSet<String> tags = new HashSet<>();
        tags.add("developer");
        tags.add("java");

        controller.createUser("John", "Doe", "john.doe@test.com", 50000.0, tags, "");

        User createdUser = userRepository.getUserByEmail("john.doe@test.com");
        assertNull(createdUser);
    }

    /**
     * Tests the creation of a user with empty tags.
     */
    @Test
    void createUserWithEmptyTags() {
        HashSet<String> tags = new HashSet<>();

        controller.createUser("John", "Doe", "john.doe@test.com", 50000.0, tags, "password123");

        User createdUser = userRepository.getUserByEmail("john.doe@test.com");
        assertNotNull(createdUser);
        assertTrue(createdUser.getTags().isEmpty());
    }

    /**
     * Tests the creation of a user with null tags.
     */
    @Test
    void createUserWithNullTags() {
        controller.createUser("John", "Doe", "john.doe@test.com", 50000.0, null, "password123");

        User createdUser = userRepository.getUserByEmail("john.doe@test.com");
        assertNotNull(createdUser);
        assertTrue(createdUser.getTags().isEmpty());
    }
}
