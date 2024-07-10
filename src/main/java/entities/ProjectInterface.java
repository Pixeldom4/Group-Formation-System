package entities;

import java.util.HashSet;

public interface ProjectInterface {

    // Getters and Setters
    int getProjectId();
    void setProjectId(int projectId);
    String getProjectTitle();
    void setProjectTitle(String projectTitle);
    double getProjectBudget();
    void setProjectBudget(double projectBudget);
    String getProjectDescription();
    void setProjectDescription(String projectDescription);
    HashSet<String> getProjectTags();
    void setProjectTags(HashSet<String> projectTags);
}
