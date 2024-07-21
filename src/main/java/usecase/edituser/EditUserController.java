package usecase.edituser;


import java.util.HashSet;

public class EditUserController {
    private final EditUserInputBoundary EditUserInteractor;

    public EditUserController(EditUserInputBoundary EditUserInteractor) {
        this.EditUserInteractor = EditUserInteractor;
    }

    /**
     * Updates the user.
     *
     * @param userId              the ID of the user.
     * @param firstName           the first name of the user.
     * @param lastName            the last name of the user.
     * @param email
     * @param desiredCompensation the desired compensation of the user.
     * @param tags                the tags for the user.
     */
     public void editUser(int userId, String firstName, String lastName, String email, double desiredCompensation, HashSet<String> tags){
         EditUserInputData inputData = new EditUserInputData(userId, firstName, lastName, desiredCompensation, tags);
         EditUserInteractor.editUser(inputData);
     }
}
