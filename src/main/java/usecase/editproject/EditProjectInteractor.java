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
        int projectId = inputData.getProjectId();
        String title = inputData.getTitle();
        double budget = inputData.getBudget();
        String description = inputData.getDescription();
        HashSet<String> tags = inputData.getTags();

        if (projectRepository.update(projectId, title, budget, description, tags, embedding)) {
            EditProjectOutputData outputData = new EditProjectOutputData(projectId, title, budget, description, tags, true);
            projectPresenter.prepareSuccessView(outputData);
        } else {
            projectPresenter.prepareFailView("Failed to edit project.");
        }
    }
}
