package usecase.getusers;

import org.apache.commons.lang3.NotImplementedException;

public class GetUsersPresenter implements GetUsersOutputBoundary {
    /**
     * Prepares the success view with the provided output data.
     *
     * @param outputData the output data to present in case of success.
     */
    @Override
    public void prepareSuccessView(GetUsersOutputData outputData) {
        throw new NotImplementedException();
    }

    /**
     * Prepares the failure view with the provided error message.
     *
     * @param errorMessage the error message to present in case of failure.
     */
    @Override
    public void prepareFailView(String errorMessage) {
        throw new NotImplementedException();
    }
}
