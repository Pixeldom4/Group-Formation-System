package usecase.manageapplications;

public class ManageApplicationsController implements ManageApplicationsInputBoundary {
    private final ManageApplicationsInteractor manageApplicationsInteractor;

    public ManageApplicationsController(ManageApplicationsInteractor manageApplicationsInteractor){
        this.manageApplicationsInteractor = manageApplicationsInteractor;
    }

    @Override
    public void getApplicationsForProject(ManageApplicationsInputData inputData) {
        manageApplicationsInteractor.getApplicationsForProject(inputData);
    }

    @Override
    public void getApplicationsForSelf(ManageApplicationsInputData inputData) {
        manageApplicationsInteractor.getApplicationsForSelf(inputData);
    }

    @Override
    public void rejectApplicant(ManageApplicationsInputData inputData) {

    }

    @Override
    public void acceptApplicant(ManageApplicationsInputData inputData) {

    }
}
