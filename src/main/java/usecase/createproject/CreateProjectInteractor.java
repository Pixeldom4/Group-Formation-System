package usecase.createproject;

import entities.Project;
import dataaccess.IProjectRepository;

public class CreateProjectInteractor implements CreateProjectInputBoundary {
    private final IProjectRepository projectRepository;
    private final CreateProjectOutputBoundary projectPresenter;

    public CreateProjectInteractor(IProjectRepository projectRepository, CreateProjectOutputBoundary projectPresenter) {
        this.projectRepository = projectRepository;
        this.projectPresenter = projectPresenter;
    }

    /**
     * Creates a project with the provided input data.
     *
     * @param inputData the input data required to create a project.
     */
    @Override
    public void createProject(CreateProjectInputData inputData) {
        float[] embeddings = {}; // need to implement generate embeddings.
        Project project = projectRepository.createProject(inputData.getTitle(), inputData.getBudget(), inputData.getDescription(), inputData.getTags(), embeddings);

        CreateProjectOutputData outputData;
        if (project != null) {
            outputData = new CreateProjectOutputData(project.getProjectId(), project.getProjectTitle(), project.getProjectBudget(), project.getProjectDescription(), project.getProjectTags(), true);
            projectPresenter.prepareSuccessView(outputData);
        } else {
            projectPresenter.prepareFailView("Failed to create project.");
        }
    }
}
