package usecase.manageapplications.acceptapplication;

/**
 * Controller class for accepting applications.
 * Interacts with the input boundary to process application acceptance.
 */
public class AcceptApplicationController {
    private final AcceptApplicationInputBoundary acceptApplicationsInteractor;

    /**
     * Constructs an AcceptApplicationController object with the specified interactor.
     *
     * @param acceptApplicationInteractor the interactor to handle application acceptance.
     */
    public AcceptApplicationController(AcceptApplicationInputBoundary acceptApplicationInteractor){
        this.acceptApplicationsInteractor = acceptApplicationInteractor;
    }

    /**
     * Accepts an applicant for a project.
     *
     * @param projectId the ID of the project.
     * @param userId the ID of the user to accept.
     */
    public void acceptApplicant(int projectId, Integer userId) {
        AcceptApplicationInputData inputData = new AcceptApplicationInputData(projectId, userId);
        acceptApplicationsInteractor.acceptApplicant(inputData);
    }
}
