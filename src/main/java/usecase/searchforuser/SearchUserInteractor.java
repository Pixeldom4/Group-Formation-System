package usecase.searchforuser;

import dataaccess.IUserRepository;
import entities.UserInterface;

public class SearchUserInteractor implements SearchUserInputBoundary {
    private SearchUserInterface userDAO = new LocalUserSearchObject();
    private SearchUserOutputBoundary presenter;

    /**
     * Creates a new SearchUserInteractor using the given output boundary.
     *
     * @param presenter The output boundary.
     */
    public SearchUserInteractor(SearchUserOutputBoundary presenter) {
        this.presenter = presenter;
    }

    /**
     * Creates a new SearchUserInteractor using the given output boundary and user repository.
     * Used for testing where files are not stored in the user folder.
     *
     * @param presenter The output boundary.
     * @param userRepository The project repository to use.
     */
    public SearchUserInteractor(SearchUserOutputBoundary presenter, IUserRepository userRepository) {
        this.presenter = presenter;
        this.userDAO = new LocalUserSearchObject(userRepository);
    }

    /**
     * Searches for a user by his email.
     *
     * @param email The email of the user to search for.
     */

    @Override
    public void searchUserByEmail(String email) {
        UserInterface user = userDAO.searchUserByEmail(email);
        presenter.presentUser(user);
    }
}
