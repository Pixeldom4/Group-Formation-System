package usecase.manageapplications;

import usecase.acceptapplication.AcceptApplicationInputBoundary;
import usecase.acceptapplication.AcceptApplicationInputData;
import usecase.getapplications.GetApplicationsInputBoundary;
import usecase.getapplications.GetApplicationsInputData;
import usecase.rejectapplication.RejectApplicationInputBoundary;
import usecase.rejectapplication.RejectApplicationInputData;

public class ManageApplicationsController {
    private final GetApplicationsInputBoundary getApplicationsInteractor;
    private final AcceptApplicationInputBoundary acceptApplicationInteractor;
    private final RejectApplicationInputBoundary rejectApplicationInteractor;

    public ManageApplicationsController(
            GetApplicationsInputBoundary getApplicationsInteractor,
            AcceptApplicationInputBoundary acceptApplicationInteractor,
            RejectApplicationInputBoundary rejectApplicationInteractor) {
        this.getApplicationsInteractor = getApplicationsInteractor;
        this.acceptApplicationInteractor = acceptApplicationInteractor;
        this.rejectApplicationInteractor = rejectApplicationInteractor;
    }

    /**
     * Retrieves applications for a specific project.
     *
     * @param projectId the ID of the project.
     */
    public void getApplicationsForProject(int projectId) {
        GetApplicationsInputData inputData = new GetApplicationsInputData(projectId);
        getApplicationsInteractor.getApplicationsForProject(inputData);
    }

    /**
     * Rejects an applicant for a specific project.
     *
     * @param projectId the ID of the project.
     * @param userId the ID of the user to be rejected.
     */
    public void rejectApplicant(int projectId, int userId) {
        RejectApplicationInputData inputData = new RejectApplicationInputData(projectId, userId);
        rejectApplicationInteractor.rejectApplicant(inputData);
    }

    /**
     * Accepts an applicant for a project.
     *
     * @param projectId the ID of the project.
     * @param userId the ID of the user to accept.
     */
    public void acceptApplicant(int projectId, Integer userId) {
        AcceptApplicationInputData inputData = new AcceptApplicationInputData(projectId, userId);
        acceptApplicationInteractor.acceptApplicant(inputData);
    }
}
