import use_case.SearchingForProjects.SearchProjectUseCaseFactory;
import view.AddProjectPanel;
import view.SearchPanel;
import view.SwitchViewButtonPanel;
import view.ViewManager;
import view_model.AddProjectPanelViewModel;
import view_model.SearchPanelViewModel;
import view_model.SwitchViewButtonPanelViewModel;
import view_model.ViewManagerModel;

import javax.swing.*;
import java.awt.*;

class Main {
    public static void main(String[] args) {

        System.out.println("Hi this is Justin");

        JFrame application = new JFrame("A Screen");
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.setSize(1200, 1200);

        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        application.add(views);

        ViewManagerModel viewManagerModel = new ViewManagerModel();
        ViewManager viewManager = new ViewManager(views, cardLayout, viewManagerModel);

        SearchPanelViewModel searchPanelViewModel = new SearchPanelViewModel();
        SearchPanel searchPanel = SearchProjectUseCaseFactory.createSearchProjectPanel(searchPanelViewModel);

        AddProjectPanelViewModel addProjectPanelModel = new AddProjectPanelViewModel();
        AddProjectPanel addProjectPanel = new AddProjectPanel(addProjectPanelModel);

        views.add(searchPanel, searchPanelViewModel.getViewName());
        views.add(addProjectPanel, addProjectPanelModel.getViewName());

        viewManagerModel.setActiveView(searchPanelViewModel.getViewName());
        viewManagerModel.firePropertyChanged();

        SwitchViewButtonPanelViewModel switchViewButtonPanelViewModel = new SwitchViewButtonPanelViewModel();
        JPanel switchButtons = new SwitchViewButtonPanel(viewManagerModel, switchViewButtonPanelViewModel);

        application.getContentPane().add(views, BorderLayout.CENTER);
        application.getContentPane().add(switchButtons, BorderLayout.SOUTH);

        application.pack();
        application.setVisible(true);

    }
}