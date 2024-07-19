package viewmodel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public class MyProjectsPanelViewModel extends ViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private Object[][] data;
    private String errorMessage;

    public MyProjectsPanelViewModel() {
        super("GetProjectsView");
    }

    public void setData(Object[][] data) {
        this.data = data;
        support.firePropertyChange("dataUpdate", null, data);
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        support.firePropertyChange("error", null, errorMessage);
    }

    @Override
    public void firePropertyChanged() {
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}