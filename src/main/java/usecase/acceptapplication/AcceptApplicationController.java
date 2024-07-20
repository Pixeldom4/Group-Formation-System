package usecase.acceptapplication;

public class AcceptApplicationController {
    private final AcceptApplicationInputBoundary acceptApplicationsInteractor;

    public AcceptApplicationController(AcceptApplicationInputBoundary acceptApplicationInteractor){
        this.acceptApplicationsInteractor = acceptApplicationInteractor;
    }

    public void acceptApplicant(int projectId, Integer integer) {
        AcceptApplicationInputData inputData = new AcceptApplicationInputData(projectId, integer);
        acceptApplicationsInteractor.acceptApplicant(inputData);
    }
}
