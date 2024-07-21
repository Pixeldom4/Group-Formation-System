package usecase.editproject;

import java.util.HashSet;

/**
 * This is an immutable data structure responsible for storing the input
 * data related to editing a project.
 */
public class EditProjectInputData {
    private final int projectId;
    private final String title;
    private final double budget;
    private final String description;
    private final HashSet<String> tags;
    private final int editorId;

    /**
     * Constructs an EditProjectInputData object with the specified details.
     *
     * @param projectId    the ID of the project to be edited.
     * @param title        the new title of the project.
     * @param budget       the new budget of the project.
     * @param description  the new description of the project.
     * @param tags         the new tags of the project.
     * @param editorId     the ID of the user editing the project.
     */
    public EditProjectInputData(int projectId, String title, double budget, String description, HashSet<String> tags, int editorId) {
        this.projectId = projectId;
        this.title = title;
        this.budget = budget;
        this.description = description;
        this.tags = tags;
        this.editorId = editorId;
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
        return tags;
    }

    /**
     * Gets the ID of the user editing the project.
     *
     * @return the ID of the user editing the project.
     */
    public int getEditorId() {
        return editorId;
    }
}
