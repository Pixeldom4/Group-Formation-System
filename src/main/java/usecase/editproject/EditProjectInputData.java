package usecase.editproject;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * This is an immutable data structure responsible for storing the input
 * data related to editing a project.
 */
public class EditProjectInputData {
    private final int projectId;
    private final String title;
    private final double budget;
    private final String description;
    private final Set<String> tags;

    /**
     * Constructs an EditProjectInputData object with the specified details.
     *
     * @param projectId   the ID of the project.
     * @param title       the title of the project.
     * @param budget      the budget allocated for the project.
     * @param description a brief description of the project.
     * @param tags        a set of tags associated with the project.
     */
    public EditProjectInputData(int projectId, String title, double budget, String description, Set<String> tags) {
        this.projectId = projectId;
        this.title = title;
        this.budget = budget;
        this.description = description;
        this.tags = Collections.unmodifiableSet(new HashSet<>(tags));
    }

    /**
     * Returns the ID of the project.
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
     * Returns an unmodifiable set of tags associated with the project.
     *
     * @return the project tags.
     */
    public Set<String> getTags() {
        return tags;
    }
}
