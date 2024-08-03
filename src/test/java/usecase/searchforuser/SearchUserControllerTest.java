package usecase.searchforuser;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the SearchUserController class.
 */
public class SearchUserControllerTest {

    /**
     * Tests that the searchUserByEmail method calls the interactor with a valid email.
     */
    @Test
    void searchUserByEmailCallsInteractorWithValidEmail() {
        SearchUserInputBoundary mockInteractor = mock(SearchUserInputBoundary.class);
        SearchUserController controller = new SearchUserController(mockInteractor);

        controller.searchUserByEmail("valid@example.com");

        verify(mockInteractor).searchUserByEmail("valid@example.com");
    }

    /**
     * Tests that the searchUserByEmail method calls the interactor with a null email.
     */
    @Test
    void searchUserByEmailCallsInteractorWithNullEmail() {
        SearchUserInputBoundary mockInteractor = mock(SearchUserInputBoundary.class);
        SearchUserController controller = new SearchUserController(mockInteractor);

        controller.searchUserByEmail(null);

        verify(mockInteractor).searchUserByEmail(null);
    }

    /**
     * Tests that the searchUserByEmail method calls the interactor with an empty email.
     */
    @Test
    void searchUserByEmailCallsInteractorWithEmptyEmail() {
        SearchUserInputBoundary mockInteractor = mock(SearchUserInputBoundary.class);
        SearchUserController controller = new SearchUserController(mockInteractor);

        controller.searchUserByEmail("");

        verify(mockInteractor).searchUserByEmail("");
    }
}