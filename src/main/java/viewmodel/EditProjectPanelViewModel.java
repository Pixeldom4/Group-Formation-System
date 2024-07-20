package viewmodel;

import java.util.HashSet;
import java.util.Set;

public class EditProjectPanelViewModel {
    private int projectId;
    private String title;
    private double budget;
    private String description;
    private HashSet<String> tags;
    private boolean success;
    private String errorMessage;

    public EditProjectPanelViewModel(int projectId, String title, double budget, String description, HashSet<String> tags) {
        this.projectId = projectId;
        this.title = title;
        this.budget = budget;
        this.description = description;
        this.tags = tags;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(HashSet<String> tags) {
        this.tags = tags;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void firePropertyChanged() {
        // Notify listeners that properties have changed (if needed)
    }
}
