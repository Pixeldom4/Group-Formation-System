package view_model;

import java.beans.PropertyChangeListener;

public abstract class ViewModel {

    private final String viewName;

    /**
     * Creates a view model with the given view name.
     * @param viewName the name of the view
     */
    public ViewModel(String viewName) {
        this.viewName = viewName;
    }

    /**
     * Returns the name of the view.
     * @return the name of the view
     */
    public String getViewName() {
        return this.viewName;
    }

    /**
     * Fires a property change event.
     * Used to notify the view that a property has changed.
     */
    public abstract void firePropertyChanged();

    /**
     * Adds a listener to the property change event.
     * Used to register listeners (usually views) to the view model.
     * @param listener the listener
     */
    public abstract void addPropertyChangeListener(PropertyChangeListener listener);
}
