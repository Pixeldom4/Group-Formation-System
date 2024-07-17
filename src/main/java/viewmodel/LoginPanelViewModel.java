package viewmodel;

import entities.User;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class LoginPanelViewModel extends ViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private boolean success;
    private int loginUser;
    private String loginName;
    private String errorMessage;

    public LoginPanelViewModel() {
        super("LoginView");
    }

    public void setLoginUser(int userid) {
        this.loginUser = userid;
    }

    public int getLoginUser() {
        return loginUser;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginName() {
        return loginName;
    }

    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("loginUser", null, success);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
