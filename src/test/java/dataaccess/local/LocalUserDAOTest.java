package dataaccess.local;

import dataaccess.IUserRepository;
import entities.User;
import entities.UserInterface;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LocalUserDAOTest {
    private final static String SAVE_LOCATION = "local_data/test/data_access/local_dao/";
    private static IUserRepository userRepository;
    private final static File saveFile = new File(SAVE_LOCATION + "users.csv");

    @BeforeAll
    public static void setUp() throws IOException {
        Files.deleteIfExists(saveFile.toPath());
        userRepository = new LocalUserRepository(SAVE_LOCATION);
        userRepository.createUser("test@test.com",
                                  "John",
                                  "Doe",
                                  new HashSet<>(Arrays.asList("Java", "Programming")),
                                  1234.5,
                                  "password");
        userRepository.createUser("test2@test.com",
                                  "Jane",
                                  "Doe",
                                  new HashSet<>(Arrays.asList("Python", "Sleeping")),
                                  5432.1,
                                  "drowssap");
    }

    @Test
    public void testGetUserById() {
        UserInterface user = userRepository.getUserById(1);
        assertEquals(user.getUserId(), 1);
        assertEquals(user.getUserEmail(), "test@test.com");
        assertEquals(user.getLastName(), "Doe");
        assertTrue(userRepository.getUserById(1).getTags().contains("Java"));
        assertEquals(user.getDesiredCompensation(), 1234.5, 0.01);
    }

    @Test
    public void testGetUserByEmail() {
        UserInterface user = userRepository.getUserByEmail("test@test.com");
        assertEquals(user.getUserId(), 1);
        assertEquals(user.getUserEmail(), "test@test.com");
        assertEquals(user.getLastName(), "Doe");
        assertTrue(userRepository.getUserById(1).getTags().contains("Java"));
        assertEquals(user.getDesiredCompensation(), 1234.5, 0.01);
    }

    @Test
    public void testUpdateUser(){
        UserInterface user = userRepository.getUserById(1);
        user.setFirstName("Joe");
        userRepository.updateUser((User) user);
        assertEquals(userRepository.getUserById(1).getFirstName(), "Joe");

    }

    @Test
    public void testAddTags(){
        userRepository.addTags(1, new HashSet<>(List.of("Cool tag")));
        assertTrue(userRepository.getUserById(1).getTags().contains("Cool tag"));
    }

    @Test
    public void testRemoveTags(){
        userRepository.removeTags(1, new HashSet<>(List.of("Programming")));
        assertFalse(userRepository.getUserById(1).getTags().contains("Programming"));
    }

    @Test
    public void testDeleteUser(){
        userRepository.deleteUser(2);
        assertNull(userRepository.getUserById(2));
    }

}
