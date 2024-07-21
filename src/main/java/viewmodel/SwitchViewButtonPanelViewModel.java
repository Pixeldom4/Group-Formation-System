package viewmodel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * ViewModel for the Switch View Button Panel.
 */
public class SwitchViewButtonPanelViewModel extends ViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * Constructs a SwitchViewButtonPanelViewModel.
     */
    public SwitchViewButtonPanelViewModel() {
        super("SwitchButtonView");
    }

    /**
     * Fires a property change event indicating the user has logged out successfully.
     */
    public void logout() {
        support.firePropertyChange("logout", null, true);
    }

    /**
     * Fires a property change event indicating the logout attempt failed.
     */
    public void logoutFail() {
        support.firePropertyChange("logout", null, false);
    }

    /**
     * Fires a property change event.
     */
    @Override
    public void firePropertyChanged() {
        // No implementation needed
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
