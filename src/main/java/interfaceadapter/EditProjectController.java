package interfaceadapter;

import usecase.editproject.EditProjectInputBoundary;
import usecase.editproject.EditProjectInputData;

import java.util.Set;

public class EditProjectController {
    private final EditProjectInputBoundary editProjectInteractor;

    public EditProjectController(EditProjectInputBoundary editProjectInteractor) {
        this.editProjectInteractor = editProjectInteractor;
    }

    public void editProject(int projectId, String title, double budget, String description, Set<String> tags) {
        EditProjectInputData inputData = new EditProjectInputData(projectId, title, budget, description, tags);
        editProjectInteractor.editProject(inputData);
    }
}
