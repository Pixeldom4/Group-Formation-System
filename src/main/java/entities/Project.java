package entities;

import java.util.HashSet;

/**
 * Represents a project entity with details about a project.
 */
public class Project implements ProjectInterface {
    private int projectId;
    private String projectTitle;
    private double projectBudget;
    private String projectDescription;
    private HashSet<String> projectTags;
    private float[] embedding;

    /**
     * Constructs a Project object with the specified details.
     *
     * @param projectId the ID of the project.
     * @param projectTitle the title of the project.
     * @param projectBudget the budget of the project.
     * @param projectDescription the description of the project.
     * @param projectTags a set of tags associated with the project.
     */
    public Project(int projectId, String projectTitle, double projectBudget, String projectDescription, HashSet<String> projectTags) {
        this.projectId = projectId;
        this.projectTitle = projectTitle;
        this.projectBudget = projectBudget;
        this.projectDescription = projectDescription;
        this.projectTags = projectTags;
    }

    /**
     * Gets the project ID.
     *
     * @return the project ID.
     */
    public int getProjectId() {
        return projectId;
    }

    /**
     * Sets the project ID.
     *
     * @param projectId the project ID.
     */
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    /**
     * Gets the project title.
     *
     * @return the project title.
     */
    public String getProjectTitle() {
        return projectTitle;
    }

    /**
     * Sets the project title.
     *
     * @param projectTitle the project title.
     */
    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    /**
     * Gets the project budget.
     *
     * @return the project budget.
     */
    public double getProjectBudget() {
        return projectBudget;
    }

    /**
     * Sets the project budget.
     *
     * @param projectBudget the project budget.
     */
    public void setProjectBudget(double projectBudget) {
        this.projectBudget = projectBudget;
    }

    /**
     * Gets the project description.
     *
     * @return the project description.
     */
    public String getProjectDescription() {
        return projectDescription;
    }

    /**
     * Sets the project description.
     *
     * @param projectDescription the project description.
     */
    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    /**
     * Gets the project tags.
     *
     * @return a set of tags associated with the project.
     */
    public HashSet<String> getProjectTags() {
        return projectTags;
    }

    /**
     * Sets the project tags.
     *
     * @param projectTags a set of tags associated with the project.
     */
    public void setProjectTags(HashSet<String> projectTags) {
        this.projectTags = projectTags;
    }

}
