package view_model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public class SearchPanelViewModel extends ViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public SearchPanelViewModel() {
        super("SearchPanelView");
    }

    @Override
    public void firePropertyChanged() {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}