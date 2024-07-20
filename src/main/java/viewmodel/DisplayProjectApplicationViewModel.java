package viewmodel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class DisplayProjectApplicationViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private Object[][] applicationData;
    private String errorMessage;
    private String senderName;

    public void setApplicationData(Object[][] applicationData) {
        this.applicationData = applicationData;
    }

    public Object[][] getApplicationData() {
        return applicationData;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void applicationResult(boolean success) {
        support.firePropertyChange("getAppSuccess", null, success);
    }

    public void setSenderName(String acceptedName) {
        this.senderName = acceptedName;
    }

    public String getSenderName() {
        return senderName;
    }

    public void acceptedResult(boolean success) {
        support.firePropertyChange("acceptSuccess", null, success);
    }

    public void rejectedResult(boolean success) {
        support.firePropertyChange("rejectSuccess", null, success);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

}
