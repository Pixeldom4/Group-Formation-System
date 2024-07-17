package viewmodel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Set;

public class EditProjectPanelViewModel {
    private boolean success;
    private int projectId;
    private String projectName;
    private double budget;
    private String description;
    private Set<String> tags;
    private String errorMessage;
    private final PropertyChangeSupport support;

    public EditProjectPanelViewModel() {
        this.support = new PropertyChangeSupport(this);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        boolean oldSuccess = this.success;
        this.success = success;
        support.firePropertyChange("success", oldSuccess, success);
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        int oldProjectId = this.projectId;
        this.projectId = projectId;
        support.firePropertyChange("projectId", oldProjectId, projectId);
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        String oldProjectName = this.projectName;
        this.projectName = projectName;
        support.firePropertyChange("projectName", oldProjectName, projectName);
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        double oldBudget = this.budget;
        this.budget = budget;
        support.firePropertyChange("budget", oldBudget, budget);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        String oldDescription = this.description;
        this.description = description;
        support.firePropertyChange("description", oldDescription, description);
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        Set<String> oldTags = this.tags;
        this.tags = tags;
        support.firePropertyChange("tags", oldTags, tags);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        String oldErrorMessage = this.errorMessage;
        this.errorMessage = errorMessage;
        support.firePropertyChange("errorMessage", oldErrorMessage, errorMessage);
    }

    public void firePropertyChanged() {
        support.firePropertyChange(null, null, null);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }
}
