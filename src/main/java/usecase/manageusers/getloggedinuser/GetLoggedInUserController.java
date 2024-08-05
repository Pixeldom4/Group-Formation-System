package usecase.manageusers.getloggedinuser;

/**
 * Controller class for getting the logged-in user.
 * Interacts with the input boundary to retrieve the logged-in user's information.
 */
public class GetLoggedInUserController {
    private final GetLoggedInUserInputBoundary getLoggedInUserInteractor;

    /**
     * Constructs a GetLoggedInUserController.
     *
     * @param getLoggedInUserInteractor the interactor that handles the get logged-in user use case
     */
    public GetLoggedInUserController(GetLoggedInUserInputBoundary getLoggedInUserInteractor) {
        this.getLoggedInUserInteractor = getLoggedInUserInteractor;
    }

    /**
     * Retrieves the logged-in user's information.
     */
    public void getLoggedInUser() {
        getLoggedInUserInteractor.getLoggedInUser();
    }
}
