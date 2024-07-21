package usecase.getprojects;

import dataaccess.DataAccessConfig;
import dataaccess.ILoginUserDetails;
import dataaccess.IProjectRepository;
import dataaccess.IUserProjectsRepository;
import entities.Project;

import java.util.HashSet;

public class GetProjectsInteractor implements GetProjectsInputBoundary {
    private final IUserProjectsRepository userProjectsRepository = DataAccessConfig.getUserProjectsRepository();
    private final IProjectRepository projectRepository = DataAccessConfig.getProjectRepository();
    private final GetProjectsOutputBoundary getProjectsPresenter;
    private final ILoginUserDetails loginUserDetails = DataAccessConfig.getLoginUserDetails();

    public GetProjectsInteractor(GetProjectsOutputBoundary getProjectsPresenter){
        this.getProjectsPresenter = getProjectsPresenter;
    }

    @Override
    public void getProjects(GetProjectsInputData inputData) {
        int loginUserId = loginUserDetails.getUserId();
        if (loginUserId == 0){
            getProjectsPresenter.prepareFailView("User not logged in");
            return;
        }

        HashSet<Integer> projectIds = userProjectsRepository.getProjectIdsForUser(loginUserId);
        Object[][] projectData = new Object[projectIds.size()][5];

        int count = 0;
        for (int projectId:  projectIds){
            Project project = projectRepository.getProjectById(projectId);

            projectData[count][0] = projectId;
            projectData[count][1] = project.getProjectTitle();
            projectData[count][2] = project.getProjectDescription();
            projectData[count][3] = project.getProjectBudget();
            projectData[count][4] = project.getProjectTags();
            count++;
        }

        getProjectsPresenter.prepareSuccessView(new GetProjectsOutputData(projectData));
    }
}
