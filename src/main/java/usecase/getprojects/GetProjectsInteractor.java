package usecase.getprojects;

import dataaccess.database.ProjectRepository;
import dataaccess.database.UserProjectsRepository;
import entities.Project;

import java.util.HashSet;

public class GetProjectsInteractor implements GetProjectsInputBoundary{
    private final UserProjectsRepository userProjectsRepository;
    private final ProjectRepository projectRepository;
    private final GetProjectsPresenter getProjectsPresenter;

    public GetProjectsInteractor(){
        userProjectsRepository = new UserProjectsRepository("database.db");
        projectRepository = new ProjectRepository("database.db", userProjectsRepository);
        getProjectsPresenter = new GetProjectsPresenter();
    }

    @Override
    public void getProjects(GetProjectsInputData inputData) {
        HashSet<Integer> projectIds = userProjectsRepository.getProjectIdsForUser(inputData.getUserId());
        String[][] projectData = new String[projectIds.size()][3];

        int count = 0;
        for (int projectId:  projectIds){
            Project project = projectRepository.getProjectById(projectId);

            projectData[count][0] = project.getProjectTitle();
            projectData[count][0] = project.getProjectDescription();
            count++;
        }
        getProjectsPresenter.returnProjects(projectData);
    }
}
