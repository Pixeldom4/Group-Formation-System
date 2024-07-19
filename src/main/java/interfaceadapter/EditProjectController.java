package interfaceadapter;

import usecase.editproject.EditProjectInputBoundary;
import usecase.editproject.EditProjectInputData;

import java.util.HashSet;

public class EditProjectController {
    private final EditProjectInputBoundary editProjectInteractor;

    public EditProjectController(EditProjectInputBoundary editProjectInteractor) {
        this.editProjectInteractor = editProjectInteractor;
    }

    public void editProject(int projectId, String title, double budget, String description, HashSet<String> tags) {
        EditProjectInputData inputData = new EditProjectInputData(projectId, title, budget, description, tags);
        editProjectInteractor.editProject(inputData);
    }
}
