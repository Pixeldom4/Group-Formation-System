package viewmodel;

import entities.User;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * ViewModel for managing the active view in the application.
 */
public class ViewManagerModel {

    private String activeViewName;

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * Gets the name of the currently active view.
     *
     * @return the name of the currently active view
     */
    public String getActiveView() {
        return activeViewName;
    }

    /**
     * Sets a view as active, which displays it to the user.
     *
     * @param activeView the name of the view to be set as active
     */
    public void setActiveView(String activeView) {
        this.activeViewName = activeView;
    }

    /**
     * Logs in the user and sets the active view to "EditMyProfile".
     */
    public void login() {
        this.activeViewName = "EditMyProfile";
        support.firePropertyChange("login", null, true);
        this.firePropertyChanged();
    }

    /**
     * Logs out the user and fires a property change event.
     */
    public void logout() {
        support.firePropertyChange("login", null, false);
    }

    /**
     * Fires an event when a project is added
     */
    public void addProjectEvent() {
        support.firePropertyChange("addProject", null, true);
    }

    /**
     * Fires a property change event indicating the active view has changed.
     */
    public void firePropertyChanged() {
        support.firePropertyChange("view", null, this.activeViewName);
    }

    /**
     * Adds a property change listener.
     *
     * @param listener the property change listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
