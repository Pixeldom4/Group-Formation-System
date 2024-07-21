package usecase.rejectapplication;

/**
 * Controller class for rejecting applications.
 * Interacts with the input boundary to process application rejection.
 */
public class RejectApplicationController {
    private final RejectApplicationInputBoundary rejectApplicationsInteractor;

    /**
     * Constructs a RejectApplicationController.
     *
     * @param rejectApplicationsInteractor the interactor that handles the reject application use case.
     */
    public RejectApplicationController(RejectApplicationInputBoundary rejectApplicationsInteractor) {
        this.rejectApplicationsInteractor = rejectApplicationsInteractor;
    }

    /**
     * Rejects an applicant for a specific project.
     *
     * @param projectId the ID of the project.
     * @param userId the ID of the user to be rejected.
     */
    public void rejectApplicant(int projectId, int userId) {
        RejectApplicationInputData inputData = new RejectApplicationInputData(projectId, userId);
        rejectApplicationsInteractor.rejectApplicant(inputData);
    }
}
