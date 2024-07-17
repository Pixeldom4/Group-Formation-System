package viewmodel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public class SwitchViewButtonPanelViewModel extends ViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public SwitchViewButtonPanelViewModel() {
        super("SwitchButtonView");
    }

    public void logout() {
        support.firePropertyChange("logout", null, true);
    }

    public void logoutFail() {
        support.firePropertyChange("logout", null, false);
    }

    @Override
    public void firePropertyChanged() {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}