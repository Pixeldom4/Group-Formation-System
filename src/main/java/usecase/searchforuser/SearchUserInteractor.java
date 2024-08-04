package usecase.searchforuser;

import dataaccess.IUserRepository;
import entities.UserInterface;

/**
 * Interactor class for searching users.
 * Implements the input boundary to handle user search logic.
 */
public class SearchUserInteractor implements SearchUserInputBoundary {
    private final SearchUserInterface userDAO;
    private final SearchUserOutputBoundary presenter;

    /**
     * Creates a new SearchUserInteractor using the given output boundary and user repository.
     * Used for testing where files are not stored in the user folder.
     *
     * @param presenter The output boundary.
     * @param userRepository The user repository to use.
     */
    public SearchUserInteractor(SearchUserOutputBoundary presenter, IUserRepository userRepository) {
        this.presenter = presenter;
        this.userDAO = new LocalUserSearchObject(userRepository);
    }

    /**
     * Searches for a user by email.
     *
     * @param email The email of the user to search for.
     */
    @Override
    public void searchUserByEmail(String email) {
        UserInterface user = userDAO.searchUserByEmail(email);
        presenter.presentUser(user);
    }
}
