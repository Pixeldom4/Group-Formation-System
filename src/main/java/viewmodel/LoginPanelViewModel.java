package viewmodel;

import entities.User;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * ViewModel for the Login Panel.
 */
public class LoginPanelViewModel extends ViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private boolean success;
    private int loginUser;
    private String loginName;
    private String errorMessage;

    /**
     * Constructs a LoginPanelViewModel.
     */
    public LoginPanelViewModel() {
        super("LoginView");
    }

    /**
     * Sets the login user ID.
     *
     * @param userid the user ID
     */
    public void setLoginUser(int userid) {
        this.loginUser = userid;
    }

    /**
     * Returns the login user ID.
     *
     * @return the login user ID
     */
    public int getLoginUser() {
        return loginUser;
    }

    /**
     * Sets the success status.
     *
     * @param success the success status
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Returns the success status.
     *
     * @return the success status
     */
    public boolean getSuccess() {
        return success;
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
     * Returns the error message.
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the login name.
     *
     * @param loginName the login name
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * Returns the login name.
     *
     * @return the login name
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * Fires a property change event for the login user.
     */
    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("loginUser", null, success);
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
