package usecase.rejectapplication;

public class RejectApplicationController {
    private final RejectApplicationInputBoundary rejectApplicationsInteractor;

    public RejectApplicationController(RejectApplicationInputBoundary rejectApplicationsInteractor){
        this.rejectApplicationsInteractor = rejectApplicationsInteractor;
    }

    public void rejectApplicant(int projectId, Integer integer) {
        RejectApplicationInputData inputData = new RejectApplicationInputData(projectId, integer);
        rejectApplicationsInteractor.rejectApplicant(inputData);
    }
}
