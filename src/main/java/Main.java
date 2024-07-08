import Entities.Project;
import use_case.SearchingForProjects.SearchProjectsPresenter;
import data_access.InMemoryProjectDAO;
import data_access.ProjectDAO;
import use_case.SearchingForProjects.SearchProjectsInteractor;
import view.AddProjectPanel;
import view.SearchPanel;
import view.SwitchViewButtonPanel;
import view.ViewManager;
import view_model.AddProjectPanelViewModel;
import view_model.SearchPanelViewModel;
import view_model.SwitchViewButtonPanelViewModel;
import view_model.ViewManagerModel;

import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

import javax.swing.*;
import java.awt.*;

class Main {
    public static void main(String[] args) {

        System.out.println("Hi this is Justin");

        JFrame application = new JFrame("A Screen");
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //application.setSize(800, 600);

        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        application.add(views);

        ViewManagerModel viewManagerModel = new ViewManagerModel();
        ViewManager viewManager = new ViewManager(views, cardLayout, viewManagerModel);

        // For the SearchProjects Use Case
        ProjectDAO projectDAO = new InMemoryProjectDAO();

        SearchProjectsPresenter presenter = new SearchProjectsPresenter(null);
        SearchProjectsInteractor interactor = new SearchProjectsInteractor(projectDAO, presenter);
        SearchPanelViewModel searchPanelModel = new SearchPanelViewModel(interactor);
        SearchPanel searchpanel = new SearchPanel(searchPanelModel);
        presenter = new SearchProjectsPresenter(searchpanel);

        // Adding some dummy projects for testing
        HashSet<String> hashset1 = new HashSet<>(Arrays.asList("Java", "Programming"));
        HashSet<String> hashset2 = new HashSet<>(Arrays.asList("Python", "Programming"));

        Project project1 = new Project(1, "Java Project", 1000.0, "A project about Java", hashset1);
        Project project2 = new Project(2, "Python Project", 2000.0, "A project about Python", hashset2);
        ((InMemoryProjectDAO) projectDAO).addProject(project1);
        ((InMemoryProjectDAO) projectDAO).addProject(project2);

        //

        SearchPanel searchPanel = new SearchPanel(searchPanelModel);

        AddProjectPanelViewModel addProjectPanelModel = new AddProjectPanelViewModel();
        AddProjectPanel addProjectPanel = new AddProjectPanel(addProjectPanelModel);

        views.add(searchPanel, searchPanelModel.getViewName());
        views.add(addProjectPanel, addProjectPanelModel.getViewName());

        viewManagerModel.setActiveView(searchPanelModel.getViewName());
        viewManagerModel.firePropertyChanged();

        SwitchViewButtonPanelViewModel switchViewButtonPanelViewModel = new SwitchViewButtonPanelViewModel();
        JPanel switchButtons = new SwitchViewButtonPanel(viewManagerModel, switchViewButtonPanelViewModel);

        application.getContentPane().add(views, BorderLayout.CENTER);
        application.getContentPane().add(switchButtons, BorderLayout.SOUTH);

        application.pack();
        application.setVisible(true);

    }
}