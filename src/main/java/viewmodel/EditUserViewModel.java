package viewmodel;

import entities.User;

import java.beans.PropertyChangeSupport;

public class EditUserViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private boolean success;
    private String errorMessage;
    private String projectName;
    private User loggedInUser;
}
