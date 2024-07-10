package use_case.manage_project;

import Entities.Project;
import data_access.DAOImplementationConfig;
import data_access.IProjectRepository;

public class SearchCommonProject implements SearchProjectInterface {
    @Override
    public Project searchProject(int projectId) {
        IProjectRepository csvDataAccessObject = DAOImplementationConfig.getProjectDataAccess();

        return csvDataAccessObject.getProjectById(projectId);
    }
}
