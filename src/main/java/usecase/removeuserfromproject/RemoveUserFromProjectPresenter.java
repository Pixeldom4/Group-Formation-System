package usecase.removeuserfromproject;

/**
 * Presenter for the Remove User From Project use case.
 * Implements the RemoveUserFromProjectOutputBoundary interface to handle the output data.
 */
public class RemoveUserFromProjectPresenter implements RemoveUserFromProjectOutputBoundary {

    /**
     * Prepares the success view with the given output data.
     *
     * @param outputData The output data to prepare the success view with.
     */
    @Override
    public void prepareSuccessView(RemoveUserFromProjectOutputData outputData) {
        // Implementation here
    }

    /**
     * Prepares the fail view with the given error message.
     *
     * @param error The error message to prepare the fail view with.
     */
    @Override
    public void prepareFailView(String error) {
        // Implementation here
    }
}
