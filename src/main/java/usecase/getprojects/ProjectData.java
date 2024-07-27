package usecase.getprojects;

import java.util.HashSet;

/**
 * Data transfer object for project data.
 */
public class ProjectData {
    private final int projectId;
    private final String projectTitle;
    private final String projectDescription;
    private final double projectBudget;
    private final HashSet<String> projectTags;
    private final boolean isProjectOwner;

    /**
     * Constructs a ProjectData object with the specified details.
     *
     * @param projectId the unique identifier of the project
     * @param projectTitle the title of the project
     * @param projectDescription the description of the project
     * @param projectBudget the budget allocated for the project
     * @param projectTags the tags associated with the project
     * @param isProjectOwner the unique identifier of the project owner
     */
    public ProjectData(int projectId, String projectTitle, String projectDescription, double projectBudget, HashSet<String> projectTags, boolean isProjectOwner) {
        this.projectId = projectId;
        this.projectTitle = projectTitle;
        this.projectDescription = projectDescription;
        this.projectBudget = projectBudget;
        this.projectTags = projectTags;
        this.isProjectOwner = isProjectOwner;
    }

    /**
     * Gets the unique identifier of the project.
     *
     * @return the project ID
     */
    public int getProjectId() {
        return this.projectId;
    }

    /**
     * Gets the title of the project.
     *
     * @return the project title
     */
    public String getProjectTitle() {
        return this.projectTitle;
    }

    /**
     * Gets the description of the project.
     *
     * @return the project description
     */
    public String getProjectDescription() {
        return this.projectDescription;
    }

    /**
     * Gets the budget allocated for the project.
     *
     * @return the project budget
     */
    public double getProjectBudget() {
        return this.projectBudget;
    }

    /**
     * Gets the tags associated with the project.
     *
     * @return the project tags
     */
    public HashSet<String> getProjectTags() {
        return this.projectTags;
    }

    /**
     * Gets the unique identifier of the project owner.
     *
     * @return the project owner ID
     */
    public boolean isProjectOwner() {
        return this.isProjectOwner;
    }
}
