package viewmodel;

import entities.User;
import usecase.getloggedinuser.LoggedInDataAccessViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashSet;

public class EditProfileViewModel extends ViewModel implements LoggedInDataAccessViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private User loggedInUser;
    private String errorMessage;

    public EditProfileViewModel() {
        super("EditMyProfile");
    }

    @Override
    public void setLoggedInUser(int userId, String firstName, String lastName, String userEmail, double desiredCompensation, HashSet<String> tags) {
        this.loggedInUser = new User(userId, firstName, lastName, userEmail, tags, desiredCompensation);
        support.firePropertyChange("userUpdate", null, this.loggedInUser);
    }

    public int getUserId(){
        return loggedInUser.getUserId();
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public String getFirstName(){
        return loggedInUser.getFirstName();
    }

    public String getLastName(){
        return loggedInUser.getLastName();
    }

    public String getUserEmail(){
        return loggedInUser.getUserEmail();
    }

    public double getDesiredCompensation(){
        return loggedInUser.getDesiredCompensation();
    }

    @Override
    public void notLoggedIn() {
        loggedInUser = null;
    }

    @Override
    public void firePropertyChanged() {

    }

    public void saveSuccess(){
        support.firePropertyChange("saveUser", null, true);
    }

    public void saveFail(String errorMessage){
        this.errorMessage = errorMessage;
        support.firePropertyChange("saveUser", null, false);
    }

    public String getErrorMessage(){
        return errorMessage;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
