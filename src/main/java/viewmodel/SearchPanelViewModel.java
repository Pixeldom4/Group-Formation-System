package viewmodel;

import entities.ProjectInterface;
import entities.UserInterface;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;


public class SearchPanelViewModel extends ViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private ArrayList<ProjectInterface> projects;
    private ArrayList<UserInterface> users;

    public SearchPanelViewModel() {
        super("SearchPanelView");
    }

    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("rankProjects", null, projects);
    }

    /**
     * Returns a list of projects that the user searched for
     * @return the list of projects
     */
    public ArrayList<ProjectInterface> getProject(){

        return projects;
    }

    /**
     * Sets the projects to what the user searched for
     * @param projects the list of projects
     */
    public void setProjects(ArrayList<ProjectInterface> projects) {
        this.projects = projects;
    }
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Sets the users to what the user searched for
     *
     * @param users the list of users
     */
    public void setUsers(ArrayList<UserInterface> users) {
        this.users = users;
    }

    /**
     * Returns a list of users that the user searched for
     *
     * @return the list of users
     */
    public ArrayList<UserInterface> getUsers() {
        return users;
    }
}