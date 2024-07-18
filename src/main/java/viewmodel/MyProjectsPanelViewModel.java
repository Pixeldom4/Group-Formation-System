package viewmodel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public class MyProjectsPanelViewModel extends ViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public MyProjectsPanelViewModel() {
        super("GetProjectsView");
    }

    @Override
    public void firePropertyChanged() {
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}