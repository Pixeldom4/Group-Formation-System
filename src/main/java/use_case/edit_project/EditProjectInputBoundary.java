package use_case.edit_project;

/**
 * The input boundary for editing a project use case.
 */
public interface EditProjectInputBoundary {
    /**
     * Edits a project with the provided input data.
     *
     * @param inputData the input data required to edit a project.
     */
    void editProject(EditProjectInputData inputData);
}
