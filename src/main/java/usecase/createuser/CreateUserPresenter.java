package usecase.createuser;

import viewmodel.CreateUserPanelViewModel;

public class CreateUserPresenter implements CreateUserOutputBoundary {
    private final CreateUserPanelViewModel createUserViewModel;

    public CreateUserPresenter(CreateUserPanelViewModel createUserViewModel) {
        this.createUserViewModel = createUserViewModel;
    }

    @Override
    public void prepareSuccessView(CreateUserOutputData outputData) {
        createUserViewModel.setSuccess(true);
        createUserViewModel.setCreatedUser(outputData.getFirstName() + " " + outputData.getLastName());
        createUserViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        createUserViewModel.setSuccess(false);
        createUserViewModel.setErrorMessage(error);
        createUserViewModel.firePropertyChanged();
    }
}
