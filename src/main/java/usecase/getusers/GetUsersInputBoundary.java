package usecase.getusers;

/**
 * Input boundary interface for getting users.
 */
public interface GetUsersInputBoundary {
    /**
     * Retrieves users based on the provided input data.
     *
     * @param inputData the input data for retrieving users.
     */
    void getUsers(GetUsersInputData inputData);
}
