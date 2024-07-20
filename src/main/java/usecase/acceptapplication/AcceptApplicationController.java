package usecase.acceptapplication;

public class AcceptApplicationController {
    private final AcceptApplicationInteractor acceptApplicationsInteractor;

    public AcceptApplicationController(AcceptApplicationInteractor acceptApplicationInteractor){
        this.acceptApplicationsInteractor = acceptApplicationInteractor;
    }

    public void acceptApplicant(int projectId, Integer integer) {
        AcceptApplicationInputData inputData = new AcceptApplicationInputData(projectId, integer);
        acceptApplicationsInteractor.acceptApplicant(inputData);
    }
}
