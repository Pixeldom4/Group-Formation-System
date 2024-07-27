package usecase.searchprojectbyid;

/**
 * Interface for the input boundary of the Search Project By ID use case.
 * Defines the method to search for a project by its ID.
 */
public interface SearchProjectByIdInputBoundary {
    /**
     * Search for a project by its ID.
     *
     * @param projectId The ID of the project to search for.
     */
    void searchProjectById(int projectId);
}
