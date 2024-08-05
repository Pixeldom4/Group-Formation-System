package usecase.manageusers;

import usecase.createuser.CreateUserInputBoundary;
import usecase.createuser.CreateUserInputData;
import usecase.deleteuser.DeleteUserInputBoundary;
import usecase.deleteuser.DeleteUserInputData;
import usecase.edituser.EditUserInputBoundary;
import usecase.edituser.EditUserInputData;
import usecase.getloggedinuser.GetLoggedInUserInputBoundary;
import usecase.getusers.GetUsersInputBoundary;
import usecase.getusers.GetUsersInputData;

import java.util.HashSet;

public class ManageUsersController {
    private final CreateUserInputBoundary createUserInteractor;
    private final DeleteUserInputBoundary deleteUserInteractor;
    private final EditUserInputBoundary editUserInteractor;
    private final GetLoggedInUserInputBoundary getLoggedInUserInteractor;
    private final GetUsersInputBoundary getUsersInteractor;

    public ManageUsersController(
            CreateUserInputBoundary createUserInteractor,
            DeleteUserInputBoundary deleteUserInteractor,
            EditUserInputBoundary editUserInteractor,
            GetLoggedInUserInputBoundary getLoggedInUserInteractor,
            GetUsersInputBoundary getUsersInteractor
    ) {
        this.createUserInteractor = createUserInteractor;
        this.deleteUserInteractor = deleteUserInteractor;
        this.editUserInteractor = editUserInteractor;
        this.getLoggedInUserInteractor = getLoggedInUserInteractor;
        this.getUsersInteractor = getUsersInteractor;
    }

    /**
     * Calls the interactor to create a new user.
     *
     * @param firstName           The first name of the user.
     * @param lastName            The last name of the user.
     * @param email               The email of the user.
     * @param desiredCompensation The desired compensation of the user.
     * @param tags                The tags of the user.
     * @param password            The password of the user.
     */
    public void createUser(String firstName, String lastName, String email, double desiredCompensation, HashSet<String> tags, String password) {
        CreateUserInputData inputData = new CreateUserInputData(firstName, lastName, email, desiredCompensation, tags, password);
        createUserInteractor.createUser(inputData);
    }

    /**
     * Deletes the user with the specific user ID.
     *
     * @param userId the ID of the user.
     */
    public void deleteUser(int userId) {
        DeleteUserInputData inputData = new DeleteUserInputData(userId);
        deleteUserInteractor.deleteUser(inputData);
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
        editUserInteractor.editUser(inputData);
    }


    /**
     * Retrieves the logged-in user's information.
     */
    public void getLoggedInUser() {
        getLoggedInUserInteractor.getLoggedInUser();
    }


    /**
     * Retrieves users based on the provided project ID.
     *
     * @param projectId the ID of the project.
     */
    public void getUsers(int projectId) {
        GetUsersInputData inputData = new GetUsersInputData(projectId);
        getUsersInteractor.getUsers(inputData);
    }
}
