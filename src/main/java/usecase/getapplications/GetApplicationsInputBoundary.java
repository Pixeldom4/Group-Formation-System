package usecase.getapplications;

public interface GetApplicationsInputBoundary {
    public void getApplicationsForProject(int userId, int projectId);
    public void getApplicationsForSelf(int userId);
}
