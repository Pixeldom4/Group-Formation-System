package usecase.searchprojectbyid;

import entities.ProjectInterface;

public interface SearchProjectByIdInterface {
    /**
     * Search for a project by its ID.
     *
     * @param projectId The ID of the project to search for.
     *
     * @return The project with the given ID.
     */
    ProjectInterface searchProjectById(int projectId);
}
