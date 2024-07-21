package usecase.createproject;

import api.EmbeddingAPIInterface;
import api.OpenAPIDataEmbed;
import dataaccess.IUserProjectsRepository;
import entities.Project;
import dataaccess.IProjectRepository;

/**
 * Interactor for the Create Project use case.
 * Handles the business logic for creating a project.
 */
public class CreateProjectInteractor implements CreateProjectInputBoundary {
    private final IProjectRepository projectRepository;
    private final CreateProjectOutputBoundary projectPresenter;
    private final EmbeddingAPIInterface embeddingAPI = new OpenAPIDataEmbed();
    private final IUserProjectsRepository userProjectsRepository;

    /**
     * Constructs a CreateApplicationInteractor with the specified repository and presenter.
     *
     * @param projectRepository the repository to interact with the database.
     * @param projectPresenter  the presenter to handle the output presentation.
     */
    public CreateProjectInteractor(IProjectRepository projectRepository,
                                   IUserProjectsRepository userProjectsRepository,
                                   CreateProjectOutputBoundary projectPresenter) {
        this.projectRepository = projectRepository;
        this.projectPresenter = projectPresenter;
        this.userProjectsRepository = userProjectsRepository;
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
        Project project = projectRepository.createProject(inputData.getTitle(), inputData.getBudget(), inputData.getDescription(), inputData.getTags(), embeddings, inputData.getCreatorUserId());

        CreateProjectOutputData outputData;

        if (project != null) {
            userProjectsRepository.addUserToProject(inputData.getCreatorUserId(), project.getProjectId());
            outputData = new CreateProjectOutputData(project.getProjectId(), project.getProjectTitle(), project.getProjectBudget(), project.getProjectDescription(), project.getProjectTags());
            projectPresenter.prepareSuccessView(outputData);
        } else {
            projectPresenter.prepareFailView("Failed to create project.");
        }
    }
}
