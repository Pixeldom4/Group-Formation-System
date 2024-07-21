package usecase.editproject;

import java.util.HashSet;

/**
 * Controller class for editing projects.
 * Interacts with the input boundary to process project edits.
 */
public class EditProjectController {
    private final EditProjectInputBoundary interactor;

    /**
     * Constructs an EditProjectController.
     *
     * @param interactor the interactor that handles the edit project use case
     */
    public EditProjectController(EditProjectInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Edits a project with the provided input data.
     *
     * @param projectId     the ID of the project to be edited.
     * @param newTitle      the new title of the project.
     * @param newBudget     the new budget of the project.
     * @param newDescription the new description of the project.
     * @param newTags       the new tags of the project.
     * @param editorId      the ID of the user editing the project.
     */
    public void editProject(int projectId, String newTitle, double newBudget, String newDescription, HashSet<String> newTags, int editorId) {
        EditProjectInputData inputData = new EditProjectInputData(projectId, newTitle, newBudget, newDescription, newTags, editorId);
        interactor.editProject(inputData);
    }
}
