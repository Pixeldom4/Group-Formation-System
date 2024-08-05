package usecase.manageprojects.editproject;

/**
 * Input boundary interface for editing projects.
 * Defines the method to edit a project.
 */
public interface EditProjectInputBoundary {
    /**
     * Edits a project with the provided input data.
     *
     * @param inputData the input data required to edit a project.
     */
    void editProject(EditProjectInputData inputData);
}
