package usecase.createproject;

import api.EmbeddingAPIInterface;
import api.OpenAPIDataEmbed;
import entities.Project;
import dataaccess.IProjectRepository;

public class CreateProjectInteractor implements CreateProjectInputBoundary {
    private final IProjectRepository projectRepository;
    private final CreateProjectOutputBoundary projectPresenter;
    private final EmbeddingAPIInterface embeddingAPI = new OpenAPIDataEmbed();

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
        String stringToEmbed = inputData.getDescription();
        float[] embeddings = embeddingAPI.getEmbedData(stringToEmbed);
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
