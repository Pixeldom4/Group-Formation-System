package usecase.edituser;


import usecase.editproject.EditProjectInputData;

public class EditUserController {
    private final EditUserInputBoundary EditUserInteractor;

    public EditUserController(EditUserInputBoundary EditUserInteractor) {
        this.EditUserInteractor = EditUserInteractor;
    }

    /**
     * Calls the interactor to edit the user profile.
     *
     * @param FirstName the first name of the user.
     * @param LastName the last name of the user.
     * @param DesiredCompensation the desired compensation of the user.
     */
     public editUser(String firstname, String lastname, double desiredCompensation){
         EditUserInputData inputData = new EditUserInputData(firstname, lastname, desiredCompensation);
         EditUserInteractor.editUser(inputData);
     }
}
