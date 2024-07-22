package usecase.searchprojectbyid;

import entities.ProjectInterface;

/**
 * Interface for searching a project by its ID.
 */
public interface SearchProjectByIdInterface {
    /**
     * Search for a project by its ID.
     *
     * @param projectId The ID of the project to search for.
     * @return The project with the given ID.
     */
    ProjectInterface searchProjectById(int projectId);
}
