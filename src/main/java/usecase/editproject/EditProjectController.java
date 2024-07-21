package usecase.editproject;

import java.util.HashSet;

public class EditProjectController {
    private final EditProjectInputBoundary interactor;

    public EditProjectController(EditProjectInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void editProject(int projectId, String newTitle, double newBudget, String newDescription, HashSet<String> newTags, int editorId) {
        EditProjectInputData inputData = new EditProjectInputData(projectId, newTitle, newBudget, newDescription, newTags, editorId);
        interactor.editProject(inputData);
    }
}
