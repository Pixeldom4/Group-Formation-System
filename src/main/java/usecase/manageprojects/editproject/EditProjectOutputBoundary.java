package usecase.manageprojects.editproject;

/**
 * Output boundary interface for editing projects.
 * Defines methods to prepare success and failure views.
 */
public interface EditProjectOutputBoundary {
    /**
     * Prepares the success view with the provided output data.
     *
     * @param outputData the output data indicating success.
     */
    void prepareSuccessView(EditProjectOutputData outputData);

    /**
     * Prepares the failure view with the provided error message.
     *
     * @param error the error message to present in case of failure.
     */
    void prepareFailView(String error);
}
