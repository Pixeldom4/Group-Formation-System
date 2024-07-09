package use_case.create_project;

import java.util.HashSet;

/**
 * This is an immutable data structure responsible for storing the input
 * data related to creating a project.
 */
public class CreateProjectInputData {
    private final String title;
    private final double budget;
    private final String description;
    private final HashSet<String> tags;

    /**
     * Constructs a CreateProjectInputData object with the specified details.
     *
     * @param title       the title of the project.
     * @param budget      the budget allocated for the project.
     * @param description a brief description of the project.
     * @param tags        a set of tags associated with the project.
     */
    public CreateProjectInputData(String title, double budget, String description, HashSet<String> tags) {
        this.title = title;
        this.budget = budget;
        this.description = description;
        this.tags = tags;
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
        return tags;
    }
}