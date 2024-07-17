package usecase.loginuser;

import org.apache.commons.lang3.NotImplementedException;
import viewmodel.LoginPanelViewModel;

public class LoginUserPresenter implements LoginUserOutputBoundary {
    private final LoginPanelViewModel loginPanelViewModel;

    public LoginUserPresenter(LoginPanelViewModel loginPanelViewModel) {
        this.loginPanelViewModel = loginPanelViewModel;
    }

    /**
     * Prepares the success view with the provided output data.
     *
     * @param outputData the output data to present in case of success.
     */
    @Override
    public void prepareSuccessView(LoginUserOutputData outputData) {
        loginPanelViewModel.setLoginUser(outputData.getUserId());
        loginPanelViewModel.setLoginName(outputData.getFirstName() + " " + outputData.getLastName());
        loginPanelViewModel.setSuccess(true);
        loginPanelViewModel.firePropertyChanged();
    }

    /**
     * Prepares the failure view with the provided error message.
     *
     * @param errorMessage the error message to present in case of failure.
     */
    @Override
    public void prepareFailView(String errorMessage) {
        loginPanelViewModel.setErrorMessage(errorMessage);
        loginPanelViewModel.setSuccess(false);
        loginPanelViewModel.firePropertyChanged();
    }
}
