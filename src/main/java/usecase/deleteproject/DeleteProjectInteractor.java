package usecase.deleteproject;

import dataaccess.DataAccessConfig;
import dataaccess.IProjectRepository;

public class DeleteProjectInteractor implements DeleteProjectInputBoundary{
    private final IProjectRepository projectRepository;
    private final DeleteProjectPresenter deleteProjectPresenter;

    public DeleteProjectInteractor(DeleteProjectPresenter deleteProjectPresenter){
        this.projectRepository = DataAccessConfig.getProjectRepository();
        this.deleteProjectPresenter = deleteProjectPresenter;
    }

    @Override
    public void deleteProject(DeleteProjectInputData inputData) {
        int projectId = inputData.getProjectId();
        projectRepository.deleteProject(projectId);
        deleteProjectPresenter.prepareSuccessView(new DeleteProjectOutputData(projectId));
    }
}
