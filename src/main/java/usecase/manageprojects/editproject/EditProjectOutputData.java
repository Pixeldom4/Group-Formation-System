package usecase.manageprojects.editproject;

import java.util.HashSet;

/**
 * This is an immutable data structure responsible for storing the output
 * data related to editing a project.
 */
public class EditProjectOutputData {
    private final int projectId;
    private final String title;
    private final double budget;
    private final String description;
    private final HashSet<String> tags;

    /**
     * Constructs an EditProjectOutputData object with the specified details.
     *
     * @param projectId    the ID of the project.
     * @param title        the new title of the project.
     * @param budget       the new budget of the project.
     * @param description  the new description of the project.
     * @param tags         the new tags of the project.
     */
    public EditProjectOutputData(int projectId, String title, double budget, String description, HashSet<String> tags) {
        this.projectId = projectId;
        this.title = title;
        this.budget = budget;
        this.description = description;
        this.tags = new HashSet<>(tags);
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
     * Gets the new title of the project.
     *
     * @return the new title of the project.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the new budget of the project.
     *
     * @return the new budget of the project.
     */
    public double getBudget() {
        return budget;
    }

    /**
     * Gets the new description of the project.
     *
     * @return the new description of the project.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the new tags of the project.
     *
     * @return the new tags of the project.
     */
    public HashSet<String> getTags() {
        return new HashSet<>(tags);
    }
}
