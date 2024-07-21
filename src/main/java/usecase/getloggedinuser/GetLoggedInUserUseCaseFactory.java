package usecase.getloggedinuser;

/**
 * Factory class for creating instances of the GetLoggedInUser use case.
 */
public class GetLoggedInUserUseCaseFactory {

    // Private constructor to prevent instantiation
    private GetLoggedInUserUseCaseFactory() {
    }

    /**
     * Creates a new GetLoggedInUserController with the given LoggedInDataAccessViewModel.
     *
     * @param loggedInDataAccessViewModel the LoggedInDataAccessViewModel.
     * @return a new GetLoggedInUserController.
     */
    public static GetLoggedInUserController create(LoggedInDataAccessViewModel loggedInDataAccessViewModel) {
        GetLoggedInUserOutputBoundary presenter = new GetLoggedInUserPresenter(loggedInDataAccessViewModel);
        GetLoggedInUserInputBoundary interactor = new GetLoggedInUserInteractor(presenter);
        return new GetLoggedInUserController(interactor);
    }
}
