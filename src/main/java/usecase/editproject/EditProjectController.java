package usecase.editproject;

public class EditProjectController {
    private final EditProjectInputBoundary interactor;

    public EditProjectController(EditProjectInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void editProject(EditProjectInputData inputData) {
        interactor.editProject(inputData);
    }
}
