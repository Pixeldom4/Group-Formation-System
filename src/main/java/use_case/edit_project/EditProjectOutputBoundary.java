package use_case.edit_project;

/**
 * The output boundary for editing a project use case.
 */
public interface EditProjectOutputBoundary {
    /**
     * Prepares the success view with the provided output data.
     *
     * @param outputData the output data to present in case of success.
     */
    void prepareSuccessView(EditProjectOutputData outputData);

    /**
     * Prepares the failure view with the provided error message.
     *
     * @param error the error message to present in case of failure.
     */
    void prepareFailView(String error);
}
