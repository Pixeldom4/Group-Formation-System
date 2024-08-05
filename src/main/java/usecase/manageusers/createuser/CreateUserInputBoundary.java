package usecase.manageusers.createuser;

/**
 * The input boundary for creating a user use case.
 */
public interface CreateUserInputBoundary {
    /**
     * Creates a user with the provided input data.
     *
     * @param inputData the input data required to create a user.
     */
    void createUser(CreateUserInputData inputData);
}
