package usecase.manageproject;

import entities.Project;
import dataaccess.DAOImplementationConfig;
import dataaccess.IProjectRepository;

public class SearchCommonProject implements SearchProjectInterface {
    @Override
    public Project searchProject(int projectId) {
        IProjectRepository csvDataAccessObject = DAOImplementationConfig.getProjectDataAccess();

        return csvDataAccessObject.getProjectById(projectId);
    }
}
