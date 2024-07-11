package interfaceadapter;

import dataaccess.database.ProjectRepository;
import dataaccess.database.UserProjectsRepository;
import entities.Project;
import usecase.getprojects.GetProjectsOutputBoundary;

import java.util.HashSet;

public class GetProjectsPresenter implements GetProjectsOutputBoundary {

    private final UserProjectsRepository userProjectsRepository;
    private final ProjectRepository projectRepository;

    public GetProjectsPresenter(){
        userProjectsRepository = new UserProjectsRepository("database.db");
        projectRepository = new ProjectRepository("database.db", userProjectsRepository);
    }

    @Override
    public String[][] getProjects(int userId){
        HashSet<Integer> projectIds = userProjectsRepository.getProjectIdsForUser(userId);
        String[][] projectData = new String[projectIds.size()][3];
//        for (int i = 0; i < projectIds.size(); i++){
//            Project project = projectRepository.getProjectById(projectIds.remove());
//
//            projectData[i]
//        }
        int count = 0;
        for (int projectId:  projectIds){
            Project project = projectRepository.getProjectById(projectId);

            projectData[count][0] = project.getProjectTitle();
            projectData[count][0] = project.getProjectDescription();
            count++;
        }
        return projectData;
    }
}
