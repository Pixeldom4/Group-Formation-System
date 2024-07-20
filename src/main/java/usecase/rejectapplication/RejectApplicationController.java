package usecase.rejectapplication;

public class RejectApplicationController {
    private final RejectApplicationInteractor rejectApplicationsInteractor;

    public RejectApplicationController(RejectApplicationInteractor rejectApplicationsInteractor){
        this.rejectApplicationsInteractor = rejectApplicationsInteractor;
    }

    public void rejectApplicant(int projectId, Integer integer) {
        RejectApplicationInputData inputData = new RejectApplicationInputData(projectId, integer);
        rejectApplicationsInteractor.rejectApplicant(inputData);
    }
}
