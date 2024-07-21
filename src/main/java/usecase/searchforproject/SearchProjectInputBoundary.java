package usecase.searchforproject;

/**
 * Input boundary interface for searching projects.
 * Defines the method to search for projects based on the given keywords.
 */
public interface SearchProjectInputBoundary {

    /**
     * Searches for projects based on the given keywords.
     *
     * @param keywords the keywords to search for.
     */
    void searchProjects(String keywords);
}
