package usecase.createuser;

import viewmodel.CreateUserPanelViewModel;

/**
 * Presenter class for creating users.
 * Implements the output boundary to prepare views for user creation.
 */
public class CreateUserPresenter implements CreateUserOutputBoundary {
    private final CreateUserPanelViewModel createUserViewModel;

    /**
     * Constructs a CreateUserPresenter with the specified view model.
     *
     * @param createUserViewModel the view model to update with creation results.
     */
    public CreateUserPresenter(CreateUserPanelViewModel createUserViewModel) {
        this.createUserViewModel = createUserViewModel;
    }

    /**
     * Prepares the success view with the provided output data.
     *
     * @param outputData the output data indicating success.
     */
    @Override
    public void prepareSuccessView(CreateUserOutputData outputData) {
        createUserViewModel.setSuccess(true);
        createUserViewModel.setCreatedUser(outputData.getFirstName() + " " + outputData.getLastName());
        createUserViewModel.firePropertyChanged();
    }

    /**
     * Prepares the failure view with the provided error message.
     *
     * @param error the error message to present in case of failure.
     */
    @Override
    public void prepareFailView(String error) {
        createUserViewModel.setSuccess(false);
        createUserViewModel.setErrorMessage(error);
        createUserViewModel.firePropertyChanged();
    }
}
