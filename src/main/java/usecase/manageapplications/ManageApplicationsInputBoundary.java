package usecase.manageapplications;

public interface ManageApplicationsInputBoundary {
    void getApplicationsForProject(ManageApplicationsInputData inputData);
    void getApplicationsForSelf(ManageApplicationsInputData inputData);
    void rejectApplicant(ManageApplicationsInputData inputData);
    void acceptApplicant(ManageApplicationsInputData inputData);
}
