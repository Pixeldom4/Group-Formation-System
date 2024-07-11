package usecase.editproject;

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
    private final boolean success;

    /**
     * Constructs an EditProjectOutputData object with the specified details.
     *
     * @param projectId   the ID of the edited project.
     * @param title       the title of the project.
     * @param budget      the budget allocated for the project.
     * @param description a brief description of the project.
     * @param tags        a set of tags associated with the project.
     * @param success     whether the project edit was successful.
     */
    public EditProjectOutputData(int projectId, String title, double budget, String description, HashSet<String> tags, boolean success) {
        this.projectId = projectId;
        this.title = title;
        this.budget = budget;
        this.description = description;
        this.tags = new HashSet<>(tags); // Defensive copy to ensure immutability
        this.success = success;
    }

    /**
     * Returns the ID of the edited project.
     *
     * @return the project ID.
     */
    public int getProjectId() {
        return projectId;
    }

    /**
     * Returns the title of the project.
     *
     * @return the project title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the budget allocated for the project.
     *
     * @return the project budget.
     */
    public double getBudget() {
        return budget;
    }

    /**
     * Returns the description of the project.
     *
     * @return the project description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the set of tags associated with the project.
     *
     * @return the project tags.
     */
    public HashSet<String> getTags() {
        return new HashSet<>(tags); // Return a defensive copy
    }

    /**
     * Returns whether the project edit was successful.
     *
     * @return true if the project edit was successful, otherwise false.
     */
    public boolean isSuccess() {
        return success;
    }
}
