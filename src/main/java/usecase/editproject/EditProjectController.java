package usecase.editproject;

import java.util.HashSet;

public class EditProjectController {
    private final EditProjectInputBoundary editProjectInteractor;

    /**
     * Constructs an EditProjectController object with the specified interactor.
     *
     * @param editProjectInteractor the interactor to handle edit project requests.
     */
    public EditProjectController(EditProjectInputBoundary editProjectInteractor) {
        this.editProjectInteractor = editProjectInteractor;
    }

    /**
     * Handles the request to edit a project by creating an input data object and passing it to the interactor.
     *
     * @param projectId   the ID of the project to be edited.
     * @param title       the new title of the project.
     * @param budget      the new budget allocated for the project.
     * @param description a brief description of the project.
     * @param tags        a set of new tags associated with the project.
     * @param editorId    the ID of the user requesting to edit the project.
     */
    public void editProject(int projectId, String title, double budget, String description, HashSet<String> tags, int editorId) {
        EditProjectInputData inputData = new EditProjectInputData(projectId, title, budget, description, tags, editorId);
        editProjectInteractor.editProject(inputData);
    }
}
