package viewmodel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * ViewModel for displaying project applications.
 */
public class DisplayProjectApplicationViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private Object[][] applicationData;
    private String errorMessage;
    private String senderName;

    /**
     * Sets the application data.
     *
     * @param applicationData the application data
     */
    public void setApplicationData(Object[][] applicationData) {
        this.applicationData = applicationData;
    }

    /**
     * Returns the application data.
     *
     * @return the application data
     */
    public Object[][] getApplicationData() {
        return applicationData;
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
     * Fires a property change event for the application result.
     *
     * @param success the success status of the application result
     */
    public void applicationResult(boolean success) {
        support.firePropertyChange("getAppSuccess", null, success);
    }

    /**
     * Sets the sender name.
     *
     * @param acceptedName the name of the sender
     */
    public void setSenderName(String acceptedName) {
        this.senderName = acceptedName;
    }

    /**
     * Returns the sender name.
     *
     * @return the sender name
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     * Fires a property change event for the accepted result.
     *
     * @param success the success status of the accepted result
     */
    public void acceptedResult(boolean success) {
        support.firePropertyChange("acceptSuccess", null, success);
    }

    /**
     * Fires a property change event for the rejected result.
     *
     * @param success the success status of the rejected result
     */
    public void rejectedResult(boolean success) {
        support.firePropertyChange("rejectSuccess", null, success);
    }

    /**
     * Adds a property change listener.
     *
     * @param listener the property change listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Removes a property change listener.
     *
     * @param listener the property change listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
