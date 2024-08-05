package usecase.searchforuser;

import dataaccess.IUserRepository;
import entities.User;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.mockito.Mockito.*;

/**
 * Unit tests for the SearchUserInteractor class.
 */
public class SearchUserInteractorTest {

    /**
     * Tests that the searchUserByEmail method calls the presenter with the correct user when the email exists in the repository.
     */
    @Test
    void searchUserByEmailCallsPresenterWithCorrectUser() {
        IUserRepository mockRepository = mock(IUserRepository.class);
        SearchUserOutputBoundary mockPresenter = mock(SearchUserOutputBoundary.class);
        User mockUser = new User(1, "test@example.com", "FirstName", "LastName", new HashSet<>(), 0.0);
        when(mockRepository.getUserByEmail("test@example.com")).thenReturn(mockUser);

        SearchUserInteractor interactor = new SearchUserInteractor(mockPresenter, mockRepository);
        interactor.searchUserByEmail("test@example.com");

        verify(mockPresenter).presentUser(mockUser);
    }

    /**
     * Tests that the searchUserByEmail method calls the presenter with null when the email does not exist in the repository.
     */
    @Test
    void searchUserByEmailCallsPresenterWithNullForNonExistentEmail() {
        IUserRepository mockRepository = mock(IUserRepository.class);
        SearchUserOutputBoundary mockPresenter = mock(SearchUserOutputBoundary.class);
        when(mockRepository.getUserByEmail("nonexistent@example.com")).thenReturn(null);

        SearchUserInteractor interactor = new SearchUserInteractor(mockPresenter, mockRepository);
        interactor.searchUserByEmail("nonexistent@example.com");

        verify(mockPresenter).presentUser(null);
    }

    /**
     * Tests that the searchUserByEmail method calls the presenter with null when the email input is null.
     */
    @Test
    void searchUserByEmailCallsPresenterWithNullForNullEmail() {
        IUserRepository mockRepository = mock(IUserRepository.class);
        SearchUserOutputBoundary mockPresenter = mock(SearchUserOutputBoundary.class);
        when(mockRepository.getUserByEmail(null)).thenReturn(null);

        SearchUserInteractor interactor = new SearchUserInteractor(mockPresenter, mockRepository);
        interactor.searchUserByEmail(null);

        verify(mockPresenter).presentUser(null);
    }

    /**
     * Tests that the searchUserByEmail method calls the presenter with null when the email input is empty.
     */
    @Test
    void searchUserByEmailCallsPresenterWithNullForEmptyEmail() {
        IUserRepository mockRepository = mock(IUserRepository.class);
        SearchUserOutputBoundary mockPresenter = mock(SearchUserOutputBoundary.class);
        when(mockRepository.getUserByEmail("")).thenReturn(null);

        SearchUserInteractor interactor = new SearchUserInteractor(mockPresenter, mockRepository);
        interactor.searchUserByEmail("");

        verify(mockPresenter).presentUser(null);
    }
}