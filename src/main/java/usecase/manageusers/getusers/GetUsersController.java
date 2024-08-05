package usecase.manageusers.getusers;

/**
 * Controller for handling user retrieval requests.
 */
public class GetUsersController {
    private final GetUsersInputBoundary getUsersInteractor;

    /**
     * Constructs a GetUsersController with the specified interactor.
     *
     * @param getUsersInteractor the interactor for retrieving users.
     */
    public GetUsersController(GetUsersInputBoundary getUsersInteractor) {
        this.getUsersInteractor = getUsersInteractor;
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
