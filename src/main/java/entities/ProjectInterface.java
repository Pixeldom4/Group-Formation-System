package entities;

import java.util.HashSet;

/**
 * Interface for the Project entity.
 */
public interface ProjectInterface {

    /**
     * Gets the project ID.
     *
     * @return the project ID.
     */
    int getProjectId();

    /**
     * Sets the project ID.
     *
     * @param projectId the project ID.
     */
    void setProjectId(int projectId);

    /**
     * Gets the project title.
     *
     * @return the project title.
     */
    String getProjectTitle();

    /**
     * Sets the project title.
     *
     * @param projectTitle the project title.
     */
    void setProjectTitle(String projectTitle);

    /**
     * Gets the project budget.
     *
     * @return the project budget.
     */
    double getProjectBudget();

    /**
     * Sets the project budget.
     *
     * @param projectBudget the project budget.
     */
    void setProjectBudget(double projectBudget);

    /**
     * Gets the project description.
     *
     * @return the project description.
     */
    String getProjectDescription();

    /**
     * Sets the project description.
     *
     * @param projectDescription the project description.
     */
    void setProjectDescription(String projectDescription);

    /**
     * Gets the project tags.
     *
     * @return a set of tags associated with the project.
     */
    HashSet<String> getProjectTags();

    /**
     * Sets the project tags.
     *
     * @param projectTags a set of tags associated with the project.
     */
    void setProjectTags(HashSet<String> projectTags);
}
