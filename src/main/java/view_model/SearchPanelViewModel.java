package view_model;

import Entities.ProjectInterface;
import use_case.SearchingForProjects.SearchProjectsInteractor;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;


public class SearchPanelViewModel extends ViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private ArrayList<ProjectInterface> projects;

    public SearchPanelViewModel() {
        super("SearchPanelView");
    }

    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("rankProjects", null, projects);
    }

    public ArrayList<ProjectInterface> getProject(){

        return projects;
    }

    public void setProjects(ArrayList<ProjectInterface> projects) {
        this.projects = projects;
    }
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}