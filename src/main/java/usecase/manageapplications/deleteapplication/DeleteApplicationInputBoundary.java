package usecase.manageapplications.deleteapplication;

/**
 * The input boundary for deleting application use case.
 */
public interface DeleteApplicationInputBoundary {
    /**
     * Deletes an application with the provided input data.
     *
     * @param inputData the input data required to delete the application.
     */
    void deleteApplication(DeleteApplicationInputData inputData);
}
