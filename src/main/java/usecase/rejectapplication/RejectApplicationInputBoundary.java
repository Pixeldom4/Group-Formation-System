package usecase.rejectapplication;

/**
 * Input boundary interface for rejecting applications.
 * Defines the method to reject an applicant.
 */
public interface RejectApplicationInputBoundary {
    /**
     * Rejects an applicant for a specific project.
     *
     * @param inputData the input data required to reject an applicant.
     */
    void rejectApplicant(RejectApplicationInputData inputData);
}
