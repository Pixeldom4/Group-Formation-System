package usecase.manageprojects.createproject;

import java.util.HashSet;

/**
 * Controller class for creating projects.
 * Interacts with the input boundary to process project creation.
 */
public class CreateProjectController {
    private final CreateProjectInputBoundary createProjectInteractor;

    /**
     * Constructs a CreateProjectController.
     *
     * @param createProjectInteractor the interactor that handles the create project use case
     */
    public CreateProjectController(CreateProjectInputBoundary createProjectInteractor) {
        this.createProjectInteractor = createProjectInteractor;
    }

    /**
     * Calls the interactor to create a new project.
     *
     * @param title the title of the project.
     * @param budget the budget of the project.
     * @param description the description of the project.
     * @param tags the tags of the project.
     * @param creatorUserId the user ID of the user creating the project.
     */
    public void createProject(String title, double budget, String description, HashSet<String> tags, int creatorUserId) {
        CreateProjectInputData inputData = new CreateProjectInputData(title, budget, description, tags, creatorUserId);
        createProjectInteractor.createProject(inputData);
    }
}
