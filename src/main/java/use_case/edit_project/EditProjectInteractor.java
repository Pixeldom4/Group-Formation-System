package use_case.edit_project;

import Entities.Project;
import data_access.IProjectRepository;

import java.util.HashSet;

public class EditProjectInteractor implements EditProjectInputBoundary {
    private final IProjectRepository projectRepository;
    private final EditProjectOutputBoundary projectPresenter;

    public EditProjectInteractor(IProjectRepository projectRepository, EditProjectOutputBoundary projectPresenter) {
        this.projectRepository = projectRepository;
        this.projectPresenter = projectPresenter;
    }

    /**
     * Edits a project with the provided input data.
     *
     * @param inputData the input data required to edit a project.
     */
    @Override
    public void editProject(EditProjectInputData inputData) {
        float[] embedding = projectRepository.getAllEmbeddings().get(inputData.getProjectId());
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
