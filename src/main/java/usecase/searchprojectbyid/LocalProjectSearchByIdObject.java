package usecase.searchprojectbyid;

import config.DataAccessConfig;
import dataaccess.IProjectRepository;
import entities.Project;
import entities.ProjectInterface;

/**
 * Class responsible for searching projects by their ID using a local project repository.
 */
public class LocalProjectSearchByIdObject implements SearchProjectByIdInterface {
    private IProjectRepository projectDataAccess = DataAccessConfig.getProjectRepository();

    /**
     * Creates a new LocalProjectSearchByIdObject.
     */
    public LocalProjectSearchByIdObject() {
    }

    /**
     * Creates a new LocalProjectSearchByIdObject using the given project repository.
     * Used for testing where files are not stored in the project folder.
     *
     * @param projectRepository The project repository to use.
     */
    public LocalProjectSearchByIdObject(IProjectRepository projectRepository) {
        projectDataAccess = projectRepository;
    }

    /**
     * Searches for a project by its ID.
     *
     * @param projectId The ID of the project to search for.
     * @return The project found, or null if no project with the given ID exists.
     */
    @Override
    public ProjectInterface searchProjectById(int projectId) {
        Project result = projectDataAccess.getProjectById(projectId);
        return result;
    }
}
