package usecase.manageusers.edituser;

import dataaccess.IUserRepository;

import java.util.HashSet;

/**
 * Interactor for the Edit User use case.
 * Implements the EditUserInputBoundary interface to handle the input data.
 */
public class EditUserInteractor implements EditUserInputBoundary {
    private final EditUserOutputBoundary editUserPresenter;
    private final IUserRepository userRepository;

    /**
     * Constructor for the EditUserInteractor class.
     *
     * @param editUserPresenter The presenter for the edit user use case.
     * @param userRepository    The repository for user data.
     */
    public EditUserInteractor(EditUserOutputBoundary editUserPresenter, IUserRepository userRepository) {
        this.editUserPresenter = editUserPresenter;
        this.userRepository = userRepository;
    }

    /**
     * Edits the user with the given input data.
     *
     * @param inputData The input data for editing the user.
     */
    @Override
    public void editUser(EditUserInputData inputData) {
        int userId = inputData.getUserId();
        String firstName = inputData.getFirstName();
        String lastName = inputData.getLastName();
        double desiredCompensation = inputData.getDesiredCompensation();
        HashSet<String> tags = inputData.getTags();

        if (userRepository.updateUser(userId, firstName, lastName, desiredCompensation, tags)) {
            EditUserOutputData outputData = new EditUserOutputData(userId, firstName, lastName, desiredCompensation, tags);
            editUserPresenter.prepareSuccessView(outputData);
        } else {
            editUserPresenter.prepareFailView("Failed to edit user.");
        }
    }
}
