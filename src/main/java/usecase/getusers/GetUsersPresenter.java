package usecase.getusers;

public class GetUsersPresenter implements GetUsersOutputBoundary {
    /**
     * Prepares the success view with the provided output data.
     *
     * @param outputData the output data to present in case of success.
     */
    @Override
    public void prepareSuccessView(GetUsersOutputData outputData) {
        // For now, just print the data to the console
        System.out.println("Success! Retrieved users:");
        for (UserData userData : outputData.getUsers()) {
            System.out.println(userData);
        }
    }

    /**
     * Prepares the failure view with the provided error message.
     *
     * @param errorMessage the error message to present in case of failure.
     */
    @Override
    public void prepareFailView(String errorMessage) {
        // For now, just print the error message to the console
        System.err.println("Failed to retrieve users: " + errorMessage);
    }
}
