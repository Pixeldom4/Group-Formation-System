package viewmodel;

import entities.ProjectInterface;
import entities.User;
import entities.UserInterface;
import usecase.getloggedinuser.LoggedInDataAccessViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * ViewModel for the Search Panel.
 */
public class SearchPanelViewModel extends ViewModel implements LoggedInDataAccessViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private ArrayList<ProjectInterface> projects;
    private ArrayList<UserInterface> users;
    private User loggedInUser;
    private String errorApplicationMessage;

    /**
     * Constructs a SearchPanelViewModel.
     */
    public SearchPanelViewModel() {
        super("SearchPanelView");
    }

    /**
     * Fires a property change event for ranking projects.
     */
    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("rankProjects", null, projects);
    }

    /**
     * Returns a list of projects that the user searched for.
     *
     * @return the list of projects
     */
    public ArrayList<ProjectInterface> getProject() {
        return projects;
    }

    /**
     * Sets the projects to what the user searched for.
     *
     * @param projects the list of projects
     */
    public void setProjects(ArrayList<ProjectInterface> projects) {
        this.projects = projects;
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
     * Adds a property change listener.
     *
     * @param listener the property change listener
     */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Sets the users to what the user searched for.
     *
     * @param users the list of users
     */
    public void setUsers(ArrayList<UserInterface> users) {
        this.users = users;
    }

    /**
     * Returns a list of users that the user searched for.
     *
     * @return the list of users
     */
    public ArrayList<UserInterface> getUsers() {
        return users;
    }

    /**
     * Fires a property change event indicating the application was successful.
     */
    public void successApplication() {
        support.firePropertyChange("application", null, true);
    }

    /**
     * Fires a property change event indicating the application failed.
     *
     * @param errorMessage the error message
     */
    public void errorApplication(String errorMessage) {
        this.errorApplicationMessage = errorMessage;
        support.firePropertyChange("application", null, false);
    }

    /**
     * Returns the error message for the application.
     *
     * @return the error message
     */
    public String getErrorApplicationMessage() {
        return errorApplicationMessage;
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
