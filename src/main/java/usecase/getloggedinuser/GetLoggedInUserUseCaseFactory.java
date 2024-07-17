package usecase.getloggedinuser;

public class GetLoggedInUserUseCaseFactory {

    private GetLoggedInUserUseCaseFactory() {
    }

    public static GetLoggedInUserController create(LoggedInDataAccessViewModel loggedInDataAccessViewModel) {
        GetLoggedInUserOutputBoundary presenter = new GetLoggedInUserPresenter(loggedInDataAccessViewModel);
        GetLoggedInUserInputBoundary interactor = new GetLoggedInUserInteractor(presenter);
        return new GetLoggedInUserController(interactor);
    }
}
