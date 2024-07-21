package usecase.deleteproject;

/**
 * Input boundary interface for deleting projects.
 * Defines the method to delete a project.
 */
public interface DeleteProjectInputBoundary {
    /**
     * Deletes a project.
     *
     * @param inputData the input data containing the project ID.
     */
    void deleteProject(DeleteProjectInputData inputData);
}
