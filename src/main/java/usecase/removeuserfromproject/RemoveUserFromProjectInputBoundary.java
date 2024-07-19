package usecase.removeuserfromproject;

/**
 * The input boundary for removing a user from a project use case.
 */
public interface RemoveUserFromProjectInputBoundary {
    /**
     * Removes a user from the project with the given input data.
     *
     * @param removeUserFromProjectInputData the input data required to remove a user from a project.
     */
    void removeUserFromProject(RemoveUserFromProjectInputData removeUserFromProjectInputData);
}
