package usecase.searchforuser;

import entities.UserInterface;
import viewmodel.SearchPanelViewModel;

import java.util.ArrayList;

/**
 * Presenter class for searching users.
 * Implements the output boundary to present the search results.
 */
public class SearchUserPresenter implements SearchUserOutputBoundary {
    private final SearchPanelViewModel searchPanelViewModel;

    /**
     * Constructs a SearchUserPresenter with the specified view model.
     *
     * @param viewModel The view model for the search panel.
     */
    public SearchUserPresenter(SearchPanelViewModel viewModel) {
        this.searchPanelViewModel = viewModel;
    }

    /**
     * Presents the user to the search panel.
     *
     * @param user The user to present.
     */
    @Override
    public void presentUser(UserInterface user) {
        ArrayList<UserInterface> users = new ArrayList<>();
        users.add(user);

        searchPanelViewModel.setUsers(users);
        searchPanelViewModel.firePropertyChanged();
    }
}
