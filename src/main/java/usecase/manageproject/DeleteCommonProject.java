package usecase.manageproject;

import dataaccess.DAOImplementationConfig;
import dataaccess.IProjectRepository;

public class DeleteCommonProject implements DeleteProjectInterface {
    @Override
    public void deleteProject(int projectId) {
        IProjectRepository csvDataAccessObject = DAOImplementationConfig.getProjectRepository();
        csvDataAccessObject.deleteProject(projectId);
    }
}
