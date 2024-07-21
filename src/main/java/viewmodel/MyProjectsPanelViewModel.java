package viewmodel;

import entities.User;
import usecase.getloggedinuser.LoggedInDataAccessViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashSet;


public class MyProjectsPanelViewModel extends ViewModel implements LoggedInDataAccessViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private Object[][] data;
    public String errorMessage;
    private User loggedInUser;

    public MyProjectsPanelViewModel() {
        super("GetProjectsView");
    }

    public void setData(Object[][] data) {
        this.data = data;
        support.firePropertyChange("dataUpdate", null, data);
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        support.firePropertyChange("error", null, errorMessage);
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void deleteProject() {
        support.firePropertyChange("deleteProject", null, null);
    }

    @Override
    public void firePropertyChanged() {
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