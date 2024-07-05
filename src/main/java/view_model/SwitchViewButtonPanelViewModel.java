package view_model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public class SwitchViewButtonPanelViewModel extends ViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public SwitchViewButtonPanelViewModel() {
        super("SwitchButtonView");
    }

    @Override
    public void firePropertyChanged() {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}