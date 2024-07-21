package usecase.createuser;

import java.util.HashSet;

/**
 * Controller class for creating users.
 * Interacts with the input boundary to process user creation.
 */
public class CreateUserController {
    private final CreateUserInputBoundary createUserInteractor;

    /**
     * Constructs a CreateUserController.
     *
     * @param createUserInteractor the interactor that handles the create user use case
     */
    public CreateUserController(CreateUserInputBoundary createUserInteractor) {
        this.createUserInteractor = createUserInteractor;
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
}
