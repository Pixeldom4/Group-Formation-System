package usecase.manageprojects.createproject;

/**
 * The input boundary for creating a project use case.
 */
public interface CreateProjectInputBoundary {
    /**
     * Creates a project with the provided input data.
     *
     * @param inputData the input data required to create a project.
     */
    void createProject(CreateProjectInputData inputData);
}
