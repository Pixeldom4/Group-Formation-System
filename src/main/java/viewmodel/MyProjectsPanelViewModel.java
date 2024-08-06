package viewmodel;

import entities.User;
import usecase.manageusers.getloggedinuser.LoggedInDataAccessViewModel;
import usecase.manageprojects.getprojects.ProjectData;
import usecase.manageusers.getusers.UserData;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashSet;

/**
 * ViewModel for managing the user's projects panel.
 */
public class MyProjectsPanelViewModel extends ViewModel implements LoggedInDataAccessViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private HashSet<ProjectData> data;
    private HashSet<UserData> usersData;
    private String errorMessage;
    private User loggedInUser;
    private int selectedProjectId; // New field to store the selected project ID

    /**
     * Constructs a MyProjectsPanelViewModel.
     */
    public MyProjectsPanelViewModel() {
        super("GetProjectsView");
    }

    /**
     * Sets the project data.
     *
     * @param data the project data
     */
    public void setData(HashSet<ProjectData> data) {
        this.data = data;
        support.firePropertyChange("dataUpdate", null, data);
    }

    /**
     * Sets the user data.
     *
     * @param usersData the user data
     */
    public void setUsersData(HashSet<UserData> usersData) {
        this.usersData = usersData;
        support.firePropertyChange("usersDataUpdate", null, usersData);
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
     * Fires a property change event to show error message.
     */
    public void showError() {
        support.firePropertyChange("error", null, errorMessage);
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
     * Returns the error message.
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Fires a property change event to delete a project.
     */
    public void deleteProject() {
        support.firePropertyChange("deleteProject", null, null);
    }

    /**
     * Fires a property change event.
     */
    @Override
    public void firePropertyChanged() {
        // No implementation needed
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

    /**
     * Sets the selected project ID.
     *
     * @param projectId the project ID
     */
    public void setSelectedProjectId(int projectId) {
        this.selectedProjectId = projectId;
    }

    /**
     * Gets the selected project ID.
     *
     * @return the selected project ID
     */
    public int getSelectedProjectId() {
        return selectedProjectId;
    }
}
