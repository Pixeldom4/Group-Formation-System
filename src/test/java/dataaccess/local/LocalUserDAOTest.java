package dataaccess.local;

import dataaccess.IUserRepository;
import entities.User;
import entities.UserInterface;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import usecase.BCryptPasswordHasher;
import usecase.PasswordHasher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the LocalUserDAO class.
 */
public class LocalUserDAOTest {
    private final static String SAVE_LOCATION = "local_data/test/data_access/local_dao/";
    private static IUserRepository userRepository;
    private final static File saveFile = new File(SAVE_LOCATION + "users.csv");
    private static final PasswordHasher passwordHasher = new BCryptPasswordHasher();

    /**
     * Sets up the test environment before all tests.
     *
     * @throws IOException if an I/O error occurs
     */
    @BeforeAll
    public static void setUp() throws IOException {
        Files.deleteIfExists(saveFile.toPath());
        userRepository = new LocalUserRepository(SAVE_LOCATION);
        userRepository.createUser("test@test.com",
                "John",
                "Doe",
                new HashSet<>(Arrays.asList("Java", "Programming")),
                1234.5,
                passwordHasher.hashPassword("password"));
        userRepository.createUser("test2@test.com",
                "Jane",
                "Doe",
                new HashSet<>(Arrays.asList("Python", "Sleeping")),
                5432.1,
                passwordHasher.hashPassword("drowssap"));
    }

    /**
     * Tests the retrieval of a user by their ID.
     */
    @Test
    public void testGetUserById() {
        UserInterface user = userRepository.getUserById(1);
        assertEquals(user.getUserId(), 1);
        assertEquals(user.getUserEmail(), "test@test.com");
        assertEquals(user.getLastName(), "Doe");
        assertTrue(userRepository.getUserById(1).getTags().contains("Java"));
        assertEquals(user.getDesiredCompensation(), 1234.5, 0.01);
    }

    /**
     * Tests the retrieval of a user by their email.
     */
    @Test
    public void testGetUserByEmail() {
        UserInterface user = userRepository.getUserByEmail("test@test.com");
        assertEquals(user.getUserId(), 1);
        assertEquals(user.getUserEmail(), "test@test.com");
        assertEquals(user.getLastName(), "Doe");
        assertTrue(userRepository.getUserById(1).getTags().contains("Java"));
        assertEquals(user.getDesiredCompensation(), 1234.5, 0.01);
    }

    /**
     * Tests the retrieval of a user's password by their email.
     */
    @Test
    public void testGetPasswordByEmail(){
        String hashedPassword = userRepository.getPasswordByEmail("test@test.com");
        assertTrue(passwordHasher.checkPassword("password", hashedPassword));
    }

    /**
     * Tests updating a user's details.
     */
    @Test
    public void testUpdateUser(){
        UserInterface user = userRepository.getUserById(1);
        user.setFirstName("Joe");
        userRepository.updateUser(user.getUserId(), user.getFirstName(), user.getLastName(), user.getDesiredCompensation(), user.getTags());
        assertEquals(userRepository.getUserById(1).getFirstName(), "Joe");

    }

    /**
     * Tests adding tags to a user.
     */
    @Test
    public void testAddTags(){
        userRepository.addTags(1, new HashSet<>(List.of("Cool tag")));
        assertTrue(userRepository.getUserById(1).getTags().contains("Cool tag"));
    }

    /**
     * Tests removing tags from a user.
     */
    @Test
    public void testRemoveTags(){
        userRepository.removeTags(1, new HashSet<>(List.of("Programming")));
        assertFalse(userRepository.getUserById(1).getTags().contains("Programming"));
    }

    /**
     * Tests deleting a user.
     */
    @Test
    public void testDeleteUser(){
        userRepository.deleteUser(2);
        assertNull(userRepository.getUserById(2));
    }

    /**
     * Tests retrieving from a csv file.
     */
    @Test
    public void testReadFromFile() {
        IUserRepository testRepository = new LocalUserRepository(SAVE_LOCATION);
        UserInterface user = testRepository.getUserById(1);
        assertEquals(user.getUserId(), 1);
        assertEquals(user.getUserEmail(), "test@test.com");
        assertEquals(user.getLastName(), "Doe");
    }

}
