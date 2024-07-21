package viewmodel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashSet;
import java.util.Set;

public class EditProjectPanelViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private int projectId;
    private int editorId;
    private String title;
    private double budget;
    private String description;
    private HashSet<String> tags;
    private boolean success;
    private String errorMessage;

    public EditProjectPanelViewModel() {}

    public EditProjectPanelViewModel(int projectId, String title, double budget, String description, HashSet<String> tags, int editorId) {
        setProjectDetails(projectId, title, budget, description, tags, editorId);
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getEditorId() {
        return editorId;
    }

    public void setEditorId(int editorId) {
        this.editorId = editorId;
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

    public void setProjectDetails(int projectId, String title, double budget, String description, HashSet<String> tags, int editorId) {
        this.projectId = projectId;
        this.title = title;
        this.budget = budget;
        this.description = description;
        this.tags = tags;
        this.editorId = editorId;
    }

    public void firePropertyChanged() {
        // Notify listeners that properties have changed (if needed)
        support.firePropertyChange("editSuccess", null, success);
    }

    public void initDetails() {
        // Notify listeners when the details are initialized
        support.firePropertyChange("detailInit", null, null);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
