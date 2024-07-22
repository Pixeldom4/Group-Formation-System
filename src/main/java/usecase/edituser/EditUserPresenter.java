package usecase.edituser;

import viewmodel.EditProfileViewModel;

/**
 * Presenter for the Edit User use case.
 * Implements the EditUserOutputBoundary interface to handle the output data.
 */
public class EditUserPresenter implements EditUserOutputBoundary {
    private final EditProfileViewModel editProfileViewModel;

    /**
     * Constructor for the EditUserPresenter class.
     *
     * @param editProfileViewModel The view model for the edit profile.
     */
    public EditUserPresenter(EditProfileViewModel editProfileViewModel) {
        this.editProfileViewModel = editProfileViewModel;
    }

    /**
     * Prepares the success view with the provided output data.
     *
     * @param outputData The output data to present in case of success.
     */
    @Override
    public void prepareSuccessView(EditUserOutputData outputData) {
        editProfileViewModel.saveSuccess();
    }

    /**
     * Prepares the failure view with the provided error message.
     *
     * @param error The error message to present in case of failure.
     */
    @Override
    public void prepareFailView(String error) {
        editProfileViewModel.saveFail(error);
    }
}
