package usecase.searchprojectbyid;

public interface SearchProjectByIdInputBoundary {
    /**
     * Search for a project by its ID.
     *
     * @param projectId The ID of the project to search for.
     */
    void searchProjectById(int projectId);
}
