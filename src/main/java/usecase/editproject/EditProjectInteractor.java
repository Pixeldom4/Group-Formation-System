package usecase.editproject;

import api.EmbeddingAPIInterface;
import api.OpenAPIDataEmbed;
import entities.Project;
import dataaccess.IProjectRepository;

import java.util.HashSet;

public class EditProjectInteractor implements EditProjectInputBoundary {
    private final IProjectRepository projectRepository;
    private final EditProjectOutputBoundary projectPresenter;
    private final EmbeddingAPIInterface embeddingAPI;


    public EditProjectInteractor(IProjectRepository projectRepository, EditProjectOutputBoundary projectPresenter, EmbeddingAPIInterface apiInterface) {
        this.projectRepository = projectRepository;
        this.projectPresenter = projectPresenter;
        this.embeddingAPI = new OpenAPIDataEmbed();
    }

    /**
     * Edits a project with the provided input data.
     *
     * @param inputData the input data required to edit a project.
     */
    @Override
    public void editProject(EditProjectInputData inputData) {
        float[] embedding = embeddingAPI.getEmbedData(inputData.getDescription());
        EditProjectOutputData outputData;
        try {
            projectRepository.update(inputData.getProjectId(),
                                     inputData.getTitle(),
                                     inputData.getBudget(),
                                     inputData.getDescription(),
                                     new HashSet<>(inputData.getTags()),
                                     embedding);
        } catch (Exception e) {
            projectPresenter.prepareFailView("Failed to edit project.");
        }

        Project project = projectRepository.getProjectById(inputData.getProjectId());
        outputData = new EditProjectOutputData(project.getProjectId(),
                                               project.getProjectTitle(),
                                               project.getProjectBudget(),
                                               project.getProjectDescription(),
                                               project.getProjectTags(),
                                               true);
        projectPresenter.prepareSuccessView(outputData);
    }
}
