package viewmodel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ViewManagerModel {

    private String activeViewName;

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * Gets the name of the currently active view.
     * @return the name of the currently active view
     */
    public String getActiveView() {
        return activeViewName;
    }

    /**
     * Sets a view as active, which displays it to the user.
     * @param activeView the name of the view to be set as active
     */
    public void setActiveView(String activeView) {
        this.activeViewName = activeView;
    }

    public void firePropertyChanged() {
        support.firePropertyChange("view", null, this.activeViewName);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
