package use_case.SearchingForProjects;

public interface SearchProjectInputBoundary {

    /**
     * Searches for projects based on the given keywords.
     *
     * @param keywords the keywords to search for
     */
    void searchProjects(String keywords);
}
