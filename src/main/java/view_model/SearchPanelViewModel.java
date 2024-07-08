package view_model;

import Entities.Project;
import use_case.SearchingForProjects.SearchProjectsInteractor;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;


public class SearchPanelViewModel extends ViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private SearchProjectsInteractor interactor;
    private List<Project> projects;
    public SearchPanelViewModel(SearchProjectsInteractor interactor) {
        super("SearchPanelView");
        this.interactor = interactor;
    }

    @Override
    public void firePropertyChanged() {

    }

    public List<Project> getProject(){
        return projects;
    }
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}