package use_case.SearchingForProjects;

public interface SearchProjectInputBoundary {
    void searchProjects(String keywords);
    void searchProjects(String keywords, int limit);
}
