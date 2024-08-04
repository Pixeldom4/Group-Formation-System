package usecase.searchforuser;

import dataaccess.DataAccessConfig;
import dataaccess.IUserRepository;
import viewmodel.SearchPanelViewModel;

/**
 * Factory class for creating instances of the SearchUser use case.
 */
public class SearchUserUseCaseFactory {
    private static final IUserRepository userRepository = DataAccessConfig.getUserRepository();

    private SearchUserUseCaseFactory() {
    }

    /**
     * Creates a search user by email controller.
     *
     * @param searchPanelViewModel The view model for the search panel.
     * @return The search user by email controller.
     */
    public static SearchUserController createSearchUserController(SearchPanelViewModel searchPanelViewModel) {
        SearchUserOutputBoundary presenter = new SearchUserPresenter(searchPanelViewModel);
        SearchUserInputBoundary interactor = new SearchUserInteractor(presenter, userRepository);
        return new SearchUserController(interactor);
    }
}
