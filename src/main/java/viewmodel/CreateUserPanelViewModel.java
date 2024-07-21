package viewmodel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * ViewModel for the Create User Panel.
 */
public class CreateUserPanelViewModel extends ViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private boolean success;
    private String errorMessage;
    private String createdUser;

    /**
     * Constructs a CreateUserPanelViewModel.
     */
    public CreateUserPanelViewModel(){
        super("CreateUserView");
    }

    /**
     * Sets whether the creation was successful.
     *
     * @param success the success of the creation
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Gets whether the creation was successful.
     *
     * @return the success of the creation
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the error message during the creation.
     *
     * @param errorMessage the error message
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Gets the error message during the creation.
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the created user.
     *
     * @param createdUser the name of the created user
     */
    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    /**
     * Returns the name of the created user.
     *
     * @return the name of the created user
     */
    public String getCreatedUser() {
        return createdUser;
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
}
