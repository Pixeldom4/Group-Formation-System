package usecase.searchprojectbyid;

import dataaccess.DAOImplementationConfig;
import dataaccess.IProjectRepository;
import entities.Project;
import entities.ProjectInterface;

public class LocalProjectSearchByIdObject implements SearchProjectByIdInterface {
    private IProjectRepository projectDataAccess = DAOImplementationConfig.getProjectRepository();

    public LocalProjectSearchByIdObject() {
    }

    /**
     * Creates a new LocalProjectSearchObject using the given project repository.
     * Used for testing where files are not stored in the project folder.
     *
     * @param projectRepository The project repository to use.
     */
    public LocalProjectSearchByIdObject(IProjectRepository projectRepository) {
        projectDataAccess = projectRepository;
    }

    @Override
    public ProjectInterface searchProjectById(int projectId) {
        Project result = projectDataAccess.getProjectById(projectId);
        return result;
    }
}
