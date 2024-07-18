package usecase.edituser;

import usecase.createproject.CreateProjectInputData;

/**
 * The input boundary for editing user profile use case.
 */

public interface EditUserInputBoundary {
    /**
     * Creates a project with the provided input data.
     *
     * @param inputData the input data required to create a project.
     */
    void editUser(EditUserInputBoundary inputData);

}
