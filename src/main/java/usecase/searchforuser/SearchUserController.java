package usecase.searchforuser;

public class SearchUserController {
    private final SearchUserInputBoundary interactor;

    /**
     * Constructor for the SearchUserController class.
     *
     * @param interactor The interactor to be used.
     */
    public SearchUserController(SearchUserInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Search for a user by his email.
     *
     * @param email The email of the user to search for.
     */
    public void searchUserByEmail(String email) {
        interactor.searchUserByEmail(email);
    }
}
