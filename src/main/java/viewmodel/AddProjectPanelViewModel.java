package viewmodel;

import usecase.getloggedinuser.LoggedInDataAccessViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashSet;
import entities.User;

/**
 * ViewModel for the Add Project Panel.
 */
public class AddProjectPanelViewModel extends ViewModel implements LoggedInDataAccessViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private boolean success;
    private String errorMessage;
    private String projectName;
    private User loggedInUser;

    /**
     * Constructs an AddProjectPanelViewModel.
     */
    public AddProjectPanelViewModel() {
        super("AddProjectView");
    }

    /**
     * Sets the success status.
     *
     * @param success the success status
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Returns the success status.
     *
     * @return the success status
     */
    public boolean isSuccess() {
        return success;
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
     * Returns the error message.
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the project name.
     *
     * @param projectName the project name
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Returns the project name.
     *
     * @return the project name
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Returns the logged-in user.
     *
     * @return the logged-in user
     */
    public User getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * Fires a property change event for the success property.
     */
    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("success", null, success);
    }

    /**
     * Adds a property change listener.
     *
     * @param listener the property change listener
     */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Sets the logged-in user details.
     *
     * @param userId the user ID
     * @param firstName the first name
     * @param lastName the last name
     * @param userEmail the user email
     * @param desiredCompensation the desired compensation
     * @param tags the tags associated with the user
     */
    @Override
    public void setLoggedInUser(int userId, String firstName, String lastName, String userEmail, double desiredCompensation, HashSet<String> tags) {
        loggedInUser = new User(userId, firstName, lastName, userEmail, tags, desiredCompensation);
    }

    /**
     * Sets the logged-in user to null, indicating no user is logged in.
     */
    @Override
    public void notLoggedIn() {
        loggedInUser = null;
    }
}
