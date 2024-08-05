package usecase.searchforuser;

import dataaccess.IUserRepository;
import entities.User;
import entities.UserInterface;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the LocalUserSearchObject class.
 */
public class LocalUserSearchObjectTest {

    /**
     * Tests that searchUserByEmail returns the correct user when the email exists in the repository.
     */
    @Test
    void searchUserByEmailReturnsCorrectUser() {
        IUserRepository mockRepository = mock(IUserRepository.class);
        User mockUser = new User(1, "test@example.com", "FirstName", "LastName",
                new HashSet<>(), 0.0);
        when(mockRepository.getUserByEmail("test@example.com")).thenReturn(mockUser);

        LocalUserSearchObject searchObject = new LocalUserSearchObject(mockRepository);
        UserInterface result = searchObject.searchUserByEmail("test@example.com");

        assertEquals(mockUser, result);
    }

    /**
     * Tests that searchUserByEmail returns null when the email does not exist in the repository.
     */
    @Test
    void searchUserByEmailReturnsNullForNonExistentEmail() {
        IUserRepository mockRepository = mock(IUserRepository.class);
        when(mockRepository.getUserByEmail("nonexistent@example.com")).thenReturn(null);

        LocalUserSearchObject searchObject = new LocalUserSearchObject(mockRepository);
        UserInterface result = searchObject.searchUserByEmail("nonexistent@example.com");

        assertNull(result);
    }

    /**
     * Tests that searchUserByEmail handles null email input.
     */
    @Test
    void searchUserByEmailHandlesNullEmail() {
        IUserRepository mockRepository = mock(IUserRepository.class);
        when(mockRepository.getUserByEmail(null)).thenReturn(null);

        LocalUserSearchObject searchObject = new LocalUserSearchObject(mockRepository);
        UserInterface result = searchObject.searchUserByEmail(null);

        assertNull(result);
    }

    /**
     * Tests that searchUserByEmail handles empty email input.
     */
    @Test
    void searchUserByEmailHandlesEmptyEmail() {
        IUserRepository mockRepository = mock(IUserRepository.class);
        when(mockRepository.getUserByEmail("")).thenReturn(null);

        LocalUserSearchObject searchObject = new LocalUserSearchObject(mockRepository);
        UserInterface result = searchObject.searchUserByEmail("");

        assertNull(result);
    }
}