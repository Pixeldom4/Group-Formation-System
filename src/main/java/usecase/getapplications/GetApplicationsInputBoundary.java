package usecase.getapplications;

public interface GetApplicationsInputBoundary {
    void getApplicationsForProject(GetApplicationsInputData inputData);
    void getApplicationsForSelf(GetApplicationsInputData inputData);
}
