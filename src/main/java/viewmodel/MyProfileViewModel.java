package viewmodel;

import entities.User;
import usecase.getloggedinuser.LoggedInDataAccessViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashSet;

public class MyProfileViewModel extends ViewModel implements LoggedInDataAccessViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private User loggedInUser;

    public MyProfileViewModel() {
        super("GetMyProfile");
    }

    @Override
    public void setLoggedInUser(int userId, String firstName, String lastName, String userEmail, double desiredCompensation, HashSet<String> tags) {
        this.loggedInUser = new User(userId, firstName, lastName, userEmail, tags, desiredCompensation);
    }

    @Override
    public void notLoggedIn() {
        loggedInUser = null;
    }

    @Override
    public void firePropertyChanged() {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
