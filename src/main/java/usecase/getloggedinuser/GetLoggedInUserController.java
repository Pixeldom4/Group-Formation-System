package usecase.getloggedinuser;

public class GetLoggedInUserController {
    private final GetLoggedInUserInputBoundary getLoggedInUserInteractor;

    public GetLoggedInUserController(GetLoggedInUserInputBoundary getLoggedInUserInteractor) {
        this.getLoggedInUserInteractor = getLoggedInUserInteractor;
    }

    public void getLoggedInUser() {
        getLoggedInUserInteractor.getLoggedInUser();
    }
}
