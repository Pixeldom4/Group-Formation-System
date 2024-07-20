package usecase.getapplications;

public class GetApplicationsController {
    private final GetApplicationsInputBoundary manageApplicationsInteractor;

    public GetApplicationsController(GetApplicationsInputBoundary manageApplicationsInteractor){
        this.manageApplicationsInteractor = manageApplicationsInteractor;
    }

    public void getApplicationsForProject(int projectId) {
        GetApplicationsInputData inputData = new GetApplicationsInputData(projectId);
        manageApplicationsInteractor.getApplicationsForProject(inputData);
    }

}
