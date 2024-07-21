package usecase.acceptapplication;

/**
 * Input boundary interface for accepting applications.
 * Defines the method to accept an applicant.
 */
public interface AcceptApplicationInputBoundary {
    /**
     * Accepts an applicant for a project.
     *
     * @param inputData the input data containing project and user IDs.
     */
    void acceptApplicant(AcceptApplicationInputData inputData);
}
