package viewmodel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public class AddProjectPanelViewModel extends ViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private boolean success;
    private String errorMessage;
    private String projectName;

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

    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("success", null, success);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}