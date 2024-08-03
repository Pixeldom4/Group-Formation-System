package view;

import usecase.logout.LogoutController;
import view.services.hovervoice.HoverVoiceServiceConfig;
import view.services.hovervoice.IHoverVoiceService;
import view.services.playvoice.IPlayVoiceService;
import view.services.playvoice.PlayVoiceServiceConfig;
import viewmodel.SwitchViewButtonPanelViewModel;
import viewmodel.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * A panel that contains buttons for switching views in the application.
 */
@SuppressWarnings("FieldCanBeLocal")
public class SwitchViewButtonPanel extends JPanel implements ActionListener, PropertyChangeListener {

    private final SwitchViewButtonPanelViewModel switchViewButtonPanelViewModel;
    private final ViewManagerModel viewManagerModel;
    private final LogoutController logoutController;

    private final JButton addProjectButton = new JButton("Add Project");
    private final JButton searchProjectButton = new JButton("Search Project");
    private final JButton getProjectsButton = new JButton("My Projects");
    private final JButton editUserProfileButton = new JButton("Edit My Profile");

    private final JButton createUserButton = new JButton("Create User");
    private final JButton loginUserButton = new JButton("Login User");

    private final JButton logoutButton = new JButton("Logout");

    private final IHoverVoiceService hoverVoiceService;
    private final IPlayVoiceService playVoiceService;

    /**
     * Constructs a SwitchViewButtonPanel.
     *
     * @param viewManagerModel the view manager model
     * @param switchViewButtonPanelViewModel the view model for the switch view button panel
     * @param logoutController the controller for logging out the user
     */
    public SwitchViewButtonPanel(ViewManagerModel viewManagerModel,
                                 SwitchViewButtonPanelViewModel switchViewButtonPanelViewModel,
                                 LogoutController logoutController) {
        this.logoutController = logoutController;
        this.switchViewButtonPanelViewModel = switchViewButtonPanelViewModel;
        switchViewButtonPanelViewModel.addPropertyChangeListener(this);

        this.hoverVoiceService = HoverVoiceServiceConfig.getHoverVoiceService();
        this.playVoiceService = PlayVoiceServiceConfig.getPlayVoiceService();

        this.viewManagerModel = viewManagerModel;
        viewManagerModel.addPropertyChangeListener(this);

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        addProjectButton.addActionListener(_ -> {
            viewManagerModel.setActiveView("AddProjectView");
            viewManagerModel.firePropertyChanged();
        });

        searchProjectButton.addActionListener(_ -> {
            viewManagerModel.setActiveView("SearchPanelView");
            viewManagerModel.firePropertyChanged();
        });

        getProjectsButton.addActionListener(_ -> {
            viewManagerModel.setActiveView("GetProjectsView");
            viewManagerModel.firePropertyChanged();
        });

        editUserProfileButton.addActionListener(_ -> {
            viewManagerModel.setActiveView("EditMyProfile");
            viewManagerModel.firePropertyChanged();
        });

        createUserButton.addActionListener(_ -> {
            viewManagerModel.setActiveView("CreateUserView");
            viewManagerModel.firePropertyChanged();
        });

        loginUserButton.addActionListener(_ -> {
            viewManagerModel.setActiveView("LoginView");
            viewManagerModel.firePropertyChanged();
        });

        logoutButton.addActionListener(_ -> logoutController.logout());

        hoverVoiceService.addHoverVoice(createUserButton, "Press to go to create user view");
        hoverVoiceService.addHoverVoice(loginUserButton, "Press to go to login view");
        hoverVoiceService.addHoverVoice(addProjectButton, "Press to go to add project view");
        hoverVoiceService.addHoverVoice(searchProjectButton, "Press to go to search project view");
        hoverVoiceService.addHoverVoice(getProjectsButton, "Press to go to my projects view");
        hoverVoiceService.addHoverVoice(editUserProfileButton, "Press to go to edit profile view");
        hoverVoiceService.addHoverVoice(logoutButton, "Press to logout");

        this.add(createUserButton);
        this.add(loginUserButton);

        this.add(editUserProfileButton);
        this.add(addProjectButton);
        this.add(searchProjectButton);
        this.add(getProjectsButton);

        this.add(logoutButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // No implementation needed
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("login")){
            boolean login = (boolean) evt.getNewValue();
            if (login){
                createUserButton.setVisible(false);
                loginUserButton.setVisible(false);
                addProjectButton.setVisible(true);
                searchProjectButton.setVisible(true);
                getProjectsButton.setVisible(true);
                editUserProfileButton.setVisible(true);
                logoutButton.setVisible(true);
            }
            else {
                createUserButton.setVisible(true);
                loginUserButton.setVisible(true);
                addProjectButton.setVisible(false);
                searchProjectButton.setVisible(false);
                getProjectsButton.setVisible(false);
                editUserProfileButton.setVisible(false);
                logoutButton.setVisible(false);
            }
        }

        if (evt.getPropertyName().equals("logout")) {
            boolean logout = (boolean) evt.getNewValue();
            if (logout) {
                viewManagerModel.logout();
                viewManagerModel.setActiveView("LoginView");
                viewManagerModel.firePropertyChanged();
                playVoiceService.playVoice("Logged out successfully");
                JOptionPane.showMessageDialog(this, "Logged out successfully");
            }
            else {
                playVoiceService.playVoice("Failed to logout");
                JOptionPane.showMessageDialog(this, "Failed to logout");
            }
        }
    }
}
