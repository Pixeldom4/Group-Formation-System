package usecase.editproject;

import api.EmbeddingAPIInterface;
import api.OpenAPIDataEmbed;
import dataaccess.IProjectRepository;

import java.util.HashSet;

/**
 * Interactor class for editing projects.
 * Implements the input boundary to handle project editing logic.
 */
public class EditProjectInteractor implements EditProjectInputBoundary {
    private final IProjectRepository projectRepository;
    private final EditProjectOutputBoundary projectPresenter;
    private final EmbeddingAPIInterface embeddingAPI;

    /**
     * Constructs an EditProjectInteractor with the specified repository, presenter, and embedding API.
     *
     * @param projectRepository  the project repository.
     * @param projectPresenter   the presenter to handle output.
     * @param apiInterface       the embedding API interface.
     */
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
        int editorId = inputData.getEditorId();

        if (projectRepository.getOwnerId(projectId) != editorId) {
            projectPresenter.prepareFailView("Insufficient Permissions.");
        } else if (projectRepository.update(projectId, title, budget, description, tags, embedding)) {
            EditProjectOutputData outputData = new EditProjectOutputData(projectId, title, budget, description, tags);
            projectPresenter.prepareSuccessView(outputData);
        } else {
            projectPresenter.prepareFailView("Failed to edit project.");
        }
    }
}
