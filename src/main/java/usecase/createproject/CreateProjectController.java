package usecase.createproject;

import java.util.HashSet;

public class CreateProjectController {
    private final CreateProjectInputBoundary createProjectInteractor;

    public CreateProjectController(CreateProjectInputBoundary createProjectInteractor) {
        this.createProjectInteractor = createProjectInteractor;
    }

    /**
     * Calls the interactor to create a new project.
     *
     * @param title The title of the project.
     * @param budget The budget of the project.
     * @param description The description of the project.
     * @param tags The tags of the project.
     */
    public void createProject(String title, double budget, String description, HashSet<String> tags) {
        CreateProjectInputData inputData = new CreateProjectInputData(title, budget, description, tags);
        createProjectInteractor.createProject(inputData);
    }
}
