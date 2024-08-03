package usecase.searchforuser;

/**
 * Controller class for searching users.
 * Interacts with the input boundary to process user search by email.
 */
public class SearchUserController {
    private final SearchUserInputBoundary interactor;

    /**
     * Constructs a SearchUserController.
     *
     * @param interactor The interactor that handles the search user use case.
     */
    public SearchUserController(SearchUserInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Searches for a user by email.
     *
     * @param email The email of the user to search for.
     */
    public void searchUserByEmail(String email) {
        interactor.searchUserByEmail(email);
    }

    /**
     * Gets the interactor.
     *
     * @return The interactor cast to SearchUserInteractor.
     */
    public SearchUserInteractor getInteractor() {
        return (SearchUserInteractor)this.interactor;
    }
}