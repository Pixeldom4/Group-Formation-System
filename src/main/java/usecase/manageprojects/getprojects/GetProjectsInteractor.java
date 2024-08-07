package usecase.manageprojects.getprojects;

import dataaccess.DataAccessConfig;
import dataaccess.IProjectRepository;
import dataaccess.IUserProjectsRepository;
import entities.Project;

import java.util.HashSet;

/**
 * Interactor class for retrieving projects.
 * Implements the input boundary to handle project retrieval logic.
 */
public class GetProjectsInteractor implements GetProjectsInputBoundary {
    private final IUserProjectsRepository userProjectsRepository;
    private final IProjectRepository projectRepository;
    private final GetProjectsOutputBoundary getProjectsPresenter;

    /**
     * Constructs a GetProjectsInteractor with the specified presenter.
     *
     * @param getProjectsPresenter the presenter to handle output.
     * @param userProjectsRepository the repository to handle user-project associations.
     * @param projectRepository the repository to handle project data.
     */
    public GetProjectsInteractor(GetProjectsOutputBoundary getProjectsPresenter,
                                 IUserProjectsRepository userProjectsRepository,
                                 IProjectRepository projectRepository) {
        this.getProjectsPresenter = getProjectsPresenter;
        this.userProjectsRepository = userProjectsRepository;
        this.projectRepository = projectRepository;
    }

    /**
     * Retrieves projects for the logged-in user.
     *
     * @param inputData the input data required to retrieve projects.
     */
    @Override
    public void getProjects(GetProjectsInputData inputData) {
        int loginUserId = inputData.getUserId();
        if (loginUserId == 0) {
            getProjectsPresenter.prepareFailView("User not logged in");
            return;
        }

        HashSet<Integer> projectIds = userProjectsRepository.getProjectIdsForUser(loginUserId);
        HashSet<ProjectData> projectData = new HashSet<>();

        for (int projectId : projectIds) {
            Project project = projectRepository.getProjectById(projectId);
            if (project == null) {
                continue;
            }
            boolean isProjectOwner = projectRepository.getOwnerId(projectId) == inputData.getUserId();

            String projectTitle = project.getProjectTitle();
            String projectDescription = project.getProjectDescription();
            double projectBudget = project.getProjectBudget();
            HashSet<String> projectTags = project.getProjectTags();

            projectData.add(new ProjectData(projectId, projectTitle, projectDescription, projectBudget, projectTags, isProjectOwner));
        }

        getProjectsPresenter.prepareSuccessView(new GetProjectsOutputData(projectData));
    }
}
