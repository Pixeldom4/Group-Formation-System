package use_case.manage_project;

import data_access.DAOImplementationConfig;
import data_access.ProjectDataAccessInterface;

public class DeleteCommonProject implements DeleteProjectInterface {
    @Override
    public void deleteProject(int projectId) {
        ProjectDataAccessInterface csvDataAccessObject = DAOImplementationConfig.getProjectDataAccess();
        csvDataAccessObject.delete(projectId);
    }
}
