package viewmodel;

import entities.User;
import usecase.getloggedinuser.LoggedInDataAccessViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashSet;

/**
 * ViewModel for editing the user profile.
 */
public class EditProfileViewModel extends ViewModel implements LoggedInDataAccessViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private User loggedInUser;
    private String errorMessage;

    /**
     * Constructs an EditProfileViewModel.
     */
    public EditProfileViewModel() {
        super("EditMyProfile");
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
        this.loggedInUser = new User(userId, firstName, lastName, userEmail, tags, desiredCompensation);
        support.firePropertyChange("userUpdate", null, this.loggedInUser);
    }

    /**
     * Returns the user ID.
     *
     * @return the user ID
     */
    public int getUserId(){
        return loggedInUser.getUserId();
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
     * Returns the first name of the logged-in user.
     *
     * @return the first name
     */
    public String getFirstName(){
        return loggedInUser.getFirstName();
    }

    /**
     * Returns the last name of the logged-in user.
     *
     * @return the last name
     */
    public String getLastName(){
        return loggedInUser.getLastName();
    }

    /**
     * Returns the email of the logged-in user.
     *
     * @return the user email
     */
    public String getUserEmail(){
        return loggedInUser.getUserEmail();
    }

    /**
     * Returns the desired compensation of the logged-in user.
     *
     * @return the desired compensation
     */
    public double getDesiredCompensation(){
        return loggedInUser.getDesiredCompensation();
    }

    /**
     * Sets the logged-in user to null, indicating no user is logged in.
     */
    @Override
    public void notLoggedIn() {
        loggedInUser = null;
        support.firePropertyChange("userUpdate", null, null);
    }

    /**
     * Fires a property change event.
     */
    @Override
    public void firePropertyChanged() {
        // No implementation needed
    }

    /**
     * Fires a property change event indicating the save was successful.
     */
    public void saveSuccess(){
        support.firePropertyChange("saveUser", null, true);
    }

    /**
     * Fires a property change event indicating the save failed.
     *
     * @param errorMessage the error message
     */
    public void saveFail(String errorMessage){
        this.errorMessage = errorMessage;
        support.firePropertyChange("saveUser", null, false);
    }

    /**
     * Returns the error message.
     *
     * @return the error message
     */
    public String getErrorMessage(){
        return errorMessage;
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
}
