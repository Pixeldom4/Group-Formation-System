package usecase.createapplication;

/**
 * The input boundary for creating an application use case.
 */
public interface CreateApplicationInputBoundary {
    /**
     * Creates an application with the provided input data.
     *
     * @param inputData the input data required to create a project.
     */
    void createApplication(CreateApplicationInputData inputData);
}
