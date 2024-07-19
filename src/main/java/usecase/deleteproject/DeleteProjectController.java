package usecase.deleteproject;

import dataaccess.database.ProjectRepository;

public class DeleteProjectController implements DeleteProjectInputBoundary {
    private final DeleteProjectInteractor deleteProjectInteractor;

    public DeleteProjectController(DeleteProjectInteractor deleteProjectInteractor){
        this.deleteProjectInteractor = deleteProjectInteractor;
    }

    @Override
    public void deleteProject(DeleteProjectInputData inputData) {
        deleteProjectInteractor.deleteProject(inputData);
    }
}
