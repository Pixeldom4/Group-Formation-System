package viewmodel;

import usecase.getloggedinuser.LoggedInDataAccessViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashSet;
import entities.User;

public class AddProjectPanelViewModel extends ViewModel implements LoggedInDataAccessViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private boolean success;
    private String errorMessage;
    private String projectName;
    private User loggedInUser;

    public AddProjectPanelViewModel() {
        super("AddProjectView");
    }


    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("success", null, success);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public void setLoggedInUser(int userId, String firstName, String lastName, String userEmail, double desiredCompensation, HashSet<String> tags) {
        loggedInUser = new User(userId, firstName, lastName, userEmail, tags, desiredCompensation);
    }

    @Override
    public void notLoggedIn() {
        loggedInUser = null;
    }
}