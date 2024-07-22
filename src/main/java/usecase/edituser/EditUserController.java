package usecase.edituser;

import java.util.HashSet;

/**
 * Controller for the Edit User use case.
 * Handles the input data and passes it to the interactor.
 */
public class EditUserController {
    private final EditUserInputBoundary EditUserInteractor;

    /**
     * Constructor for the EditUserController class.
     *
     * @param EditUserInteractor The interactor for the edit user use case.
     */
    public EditUserController(EditUserInputBoundary EditUserInteractor) {
        this.EditUserInteractor = EditUserInteractor;
    }

    /**
     * Updates the user.
     *
     * @param userId              the ID of the user.
     * @param firstName           the first name of the user.
     * @param lastName            the last name of the user.
     * @param email               the email of the user.
     * @param desiredCompensation the desired compensation of the user.
     * @param tags                the tags for the user.
     */
    public void editUser(int userId, String firstName, String lastName, String email, double desiredCompensation, HashSet<String> tags) {
        EditUserInputData inputData = new EditUserInputData(userId, firstName, lastName, desiredCompensation, tags);
        EditUserInteractor.editUser(inputData);
    }
}
