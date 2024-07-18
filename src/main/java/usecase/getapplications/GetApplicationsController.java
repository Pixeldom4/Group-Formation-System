package usecase.getapplications;

public class GetApplicationsController implements GetApplicationsInputBoundary{
    private final GetApplicationsInteractor getApplicationsInteractor;

    public GetApplicationsController(GetApplicationsInteractor getApplicationsInteractor){
        this.getApplicationsInteractor = getApplicationsInteractor;
    }

    @Override
    public void getApplicationsForProject(int userId, int projectId) {
        getApplicationsInteractor.getApplicationsForProject(userId, projectId);
    }

    @Override
    public void getApplicationsForSelf(int userId) {
        getApplicationsInteractor.getApplicationsForSelf(userId);
    }
}
