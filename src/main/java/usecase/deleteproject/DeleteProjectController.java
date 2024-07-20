package usecase.deleteproject;

import dataaccess.database.ProjectRepository;

public class DeleteProjectController {
    private final DeleteProjectInteractor deleteProjectInteractor;

    public DeleteProjectController(DeleteProjectInteractor deleteProjectInteractor){
        this.deleteProjectInteractor = deleteProjectInteractor;
    }

    public void deleteProject(DeleteProjectInputData inputData) {
        deleteProjectInteractor.deleteProject(inputData);
    }
}
