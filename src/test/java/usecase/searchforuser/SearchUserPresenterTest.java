package usecase.searchforuser;

import entities.UserInterface;
import org.junit.jupiter.api.Test;
import viewmodel.SearchPanelViewModel;

import static org.mockito.Mockito.*;

/**
 * Unit tests for the SearchUserPresenter class.
 */
public class SearchUserPresenterTest {

    /**
     * Tests that the presentUser method adds the user to the ViewModel and fires a property change event.
     */
    @Test
    void presentUserAddsUserToViewModel() {
        SearchPanelViewModel mockViewModel = mock(SearchPanelViewModel.class);
        SearchUserPresenter presenter = new SearchUserPresenter(mockViewModel);
        UserInterface mockUser = mock(UserInterface.class);

        presenter.presentUser(mockUser);

        verify(mockViewModel).setUsers(argThat(users -> users.size() == 1 && users.contains(mockUser)));
        verify(mockViewModel).firePropertyChanged();
    }

    /**
     * Tests that the presentUser method handles a null user by adding null to the ViewModel and firing a property change event.
     */
    @Test
    void presentUserHandlesNullUser() {
        SearchPanelViewModel mockViewModel = mock(SearchPanelViewModel.class);
        SearchUserPresenter presenter = new SearchUserPresenter(mockViewModel);

        presenter.presentUser(null);

        verify(mockViewModel).setUsers(argThat(users -> users.size() == 1 && users.contains(null)));
        verify(mockViewModel).firePropertyChanged();
    }
}