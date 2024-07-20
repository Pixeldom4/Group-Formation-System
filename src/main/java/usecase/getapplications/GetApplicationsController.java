package usecase.getapplications;

public class GetApplicationsController {
    private final GetApplicationsInteractor manageApplicationsInteractor;

    public GetApplicationsController(GetApplicationsInteractor manageApplicationsInteractor){
        this.manageApplicationsInteractor = manageApplicationsInteractor;
    }

    public void getApplicationsForProject(GetApplicationsInputData inputData) {
        manageApplicationsInteractor.getApplicationsForProject(inputData);
    }

    public void getApplicationsForSelf(GetApplicationsInputData inputData) {
        manageApplicationsInteractor.getApplicationsForSelf(inputData);
    }
}
