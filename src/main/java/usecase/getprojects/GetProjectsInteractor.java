package usecase.getprojects;

import dataaccess.DAOImplementationConfig;
import dataaccess.ILoginUserDetails;
import dataaccess.database.ProjectRepository;
import dataaccess.database.UserProjectsRepository;
import entities.Project;

import java.util.HashSet;

public class GetProjectsInteractor implements GetProjectsInputBoundary{
    private final UserProjectsRepository userProjectsRepository;
    private final ProjectRepository projectRepository;
    private final GetProjectsPresenter getProjectsPresenter;
    private final ILoginUserDetails loginUserDetails = DAOImplementationConfig.getLoginUserDetails();

    public GetProjectsInteractor(GetProjectsPresenter getProjectsPresenter){
        userProjectsRepository = new UserProjectsRepository("database.db");
        projectRepository = new ProjectRepository("database.db", userProjectsRepository);
        this.getProjectsPresenter = getProjectsPresenter;
    }

    @Override
    public void getProjects(GetProjectsInputData inputData) {

        HashSet<Integer> projectIds = userProjectsRepository.getProjectIdsForUser(loginUserDetails.getUserId());
        String[][] projectData = new String[projectIds.size()][3];

        int count = 0;
        for (int projectId:  projectIds){
            Project project = projectRepository.getProjectById(projectId);

            projectData[count][0] = project.getProjectTitle();
            projectData[count][0] = project.getProjectDescription();
            count++;
        }
        System.out.println(projectData.length);
        getProjectsPresenter.prepareSuccessView(new GetProjectsOutputData(projectData));
    }
}
