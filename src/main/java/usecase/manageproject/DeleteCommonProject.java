package usecase.manageproject;

import data_access.DAOImplementationConfig;
import data_access.IProjectRepository;

public class DeleteCommonProject implements DeleteProjectInterface {
    @Override
    public void deleteProject(int projectId) {
        IProjectRepository csvDataAccessObject = DAOImplementationConfig.getProjectDataAccess();
        csvDataAccessObject.deleteProject(projectId);
    }
}
