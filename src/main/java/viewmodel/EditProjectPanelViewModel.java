package viewmodel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashSet;
import java.util.Set;

/**
 * ViewModel for editing project details.
 */
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

    /**
     * Constructs an EditProjectPanelViewModel.
     */
    public EditProjectPanelViewModel() {}

    /**
     * Constructs an EditProjectPanelViewModel with the specified project details.
     *
     * @param projectId the project ID
     * @param title the project title
     * @param budget the project budget
     * @param description the project description
     * @param tags the tags associated with the project
     * @param editorId the ID of the editor
     */
    public EditProjectPanelViewModel(int projectId, String title, double budget, String description, HashSet<String> tags, int editorId) {
        setProjectDetails(projectId, title, budget, description, tags, editorId);
    }

    /**
     * Returns the project ID.
     *
     * @return the project ID
     */
    public int getProjectId() {
        return projectId;
    }

    /**
     * Sets the project ID.
     *
     * @param projectId the project ID
     */
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    /**
     * Returns the editor ID.
     *
     * @return the editor ID
     */
    public int getEditorId() {
        return editorId;
    }

    /**
     * Sets the editor ID.
     *
     * @param editorId the editor ID
     */
    public void setEditorId(int editorId) {
        this.editorId = editorId;
    }

    /**
     * Returns the project title.
     *
     * @return the project title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the project title.
     *
     * @param title the project title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the project budget.
     *
     * @return the project budget
     */
    public double getBudget() {
        return budget;
    }

    /**
     * Sets the project budget.
     *
     * @param budget the project budget
     */
    public void setBudget(double budget) {
        this.budget = budget;
    }

    /**
     * Returns the project description.
     *
     * @return the project description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the project description.
     *
     * @param description the project description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the tags associated with the project.
     *
     * @return the tags
     */
    public Set<String> getTags() {
        return tags;
    }

    /**
     * Sets the tags associated with the project.
     *
     * @param tags the tags
     */
    public void setTags(HashSet<String> tags) {
        this.tags = tags;
    }

    /**
     * Returns whether the operation was successful.
     *
     * @return the success status
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the success status of the operation.
     *
     * @param success the success status
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Returns the error message.
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the error message.
     *
     * @param errorMessage the error message
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Sets the project details.
     *
     * @param projectId the project ID
     * @param title the project title
     * @param budget the project budget
     * @param description the project description
     * @param tags the tags associated with the project
     * @param editorId the ID of the editor
     */
    public void setProjectDetails(int projectId, String title, double budget, String description, HashSet<String> tags, int editorId) {
        this.projectId = projectId;
        this.title = title;
        this.budget = budget;
        this.description = description;
        this.tags = tags;
        this.editorId = editorId;
    }

    /**
     * Fires a property change event indicating the success status of the edit operation.
     */
    public void firePropertyChanged() {
        support.firePropertyChange("editSuccess", null, success);
    }

    /**
     * Fires a property change event indicating the details have been initialized.
     */
    public void initDetails() {
        support.firePropertyChange("detailInit", null, null);
    }

    /**
     * Adds a property change listener.
     *
     * @param listener the property change listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
