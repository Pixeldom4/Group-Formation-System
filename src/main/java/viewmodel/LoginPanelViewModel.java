package viewmodel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class LoginPanelViewModel extends ViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public LoginPanelViewModel() {
        super("LoginView");
    }

    @Override
    public void firePropertyChanged() {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
