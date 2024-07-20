package usecase.deleteproject;

import dataaccess.database.ProjectRepository;

public class DeleteProjectController {
    private final DeleteProjectInputBoundary deleteProjectInteractor;

    public DeleteProjectController(DeleteProjectInputBoundary deleteProjectInteractor){
        this.deleteProjectInteractor = deleteProjectInteractor;
    }

    public void deleteProject(int projectId) {
        DeleteProjectInputData inputData = new DeleteProjectInputData(projectId);
        deleteProjectInteractor.deleteProject(inputData);
    }
}
