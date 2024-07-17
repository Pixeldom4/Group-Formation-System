package usecase.loginuser;

import org.apache.commons.lang3.NotImplementedException;

public class LoginUserPresenter implements LoginUserOutputBoundary {

    public LoginUserPresenter() {
        throw new NotImplementedException();
    }
    /**
     * Prepares the success view with the provided output data.
     *
     * @param outputData the output data to present in case of success.
     */
    @Override
    public void prepareSuccessView(LoginUserOutputData outputData) {
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
