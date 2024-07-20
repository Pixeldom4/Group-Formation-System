package usecase.manageapplications;

public class ManageApplicationsController {
    private final ManageApplicationsInteractor manageApplicationsInteractor;

    public ManageApplicationsController(ManageApplicationsInteractor manageApplicationsInteractor){
        this.manageApplicationsInteractor = manageApplicationsInteractor;
    }

    public void getApplicationsForProject(ManageApplicationsInputData inputData) {
        manageApplicationsInteractor.getApplicationsForProject(inputData);
    }

    public void getApplicationsForSelf(ManageApplicationsInputData inputData) {
        manageApplicationsInteractor.getApplicationsForSelf(inputData);
    }

    public void rejectApplicant(ManageApplicationsInputData inputData) {
        manageApplicationsInteractor.rejectApplicant(inputData);
    }

    public void acceptApplicant(ManageApplicationsInputData inputData) {
        manageApplicationsInteractor.acceptApplicant(inputData);
    }
}
