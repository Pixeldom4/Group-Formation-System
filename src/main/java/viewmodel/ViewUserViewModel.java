package viewmodel;

import entities.User;

import java.beans.PropertyChangeSupport;

/**
 * ViewModel for viewing user details.
 */
public class ViewUserViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private boolean success;
    private String errorMessage;
    private String projectName;
    private User loggedInUser;

    //TODO need implementation
}