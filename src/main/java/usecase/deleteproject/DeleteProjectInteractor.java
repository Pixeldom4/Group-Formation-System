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
        projectRepository.deleteProject(inputData.getUserId());
        deleteProjectPresenter.prepareSuccessView(new DeleteProjectOutputData());
    }
}
