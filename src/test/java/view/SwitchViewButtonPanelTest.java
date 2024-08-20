package view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import usecase.logout.LogoutController;
import view.services.hovervoice.IHoverVoiceService;
import view.services.playvoice.IPlayVoiceService;
import viewmodel.SwitchViewButtonPanelViewModel;
import viewmodel.ViewManagerModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class SwitchViewButtonPanelTest {

    private SwitchViewButtonPanel switchViewButtonPanel;

    @Mock
    private SwitchViewButtonPanelViewModel mockSwitchViewButtonPanelViewModel;
    @Mock
    private ViewManagerModel mockViewManagerModel;
    @Mock
    private LogoutController mockLogoutController;
    @Mock
    private IHoverVoiceService mockHoverVoiceService;
    @Mock
    private IPlayVoiceService mockPlayVoiceService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);


        // Correctly stub the void method
        doNothing().when(mockHoverVoiceService).addHoverVoice(any(JComponent.class), anyString());

        switchViewButtonPanel = new SwitchViewButtonPanel(
                mockViewManagerModel,
                mockSwitchViewButtonPanelViewModel,
                mockLogoutController
        );
    }


    @Test
    public void testAddProjectButtonAction() {
        switchViewButtonPanel.getAddProjectButton().doClick();
        verify(mockViewManagerModel).setActiveView("AddProjectView");
        verify(mockViewManagerModel).firePropertyChanged();
    }

    @Test
    public void testSearchProjectButtonAction() {
        switchViewButtonPanel.getSearchProjectButton().doClick();
        verify(mockViewManagerModel).setActiveView("SearchPanelView");
        verify(mockViewManagerModel).firePropertyChanged();
    }

    @Test
    public void testLogoutButtonAction() {
        switchViewButtonPanel.getLogoutButton().doClick();
        verify(mockLogoutController).logout();
    }

    @Test
    public void testPropertyChangeLogin() {
        PropertyChangeEvent event = new PropertyChangeEvent(this, "login", null, true);
        switchViewButtonPanel.propertyChange(event);

        verify(mockViewManagerModel, never()).setActiveView(anyString());
        verify(mockViewManagerModel, never()).firePropertyChanged();
    }

    @Test
    public void testGetAddProjectButton() {
        JButton addProjectButton = switchViewButtonPanel.getAddProjectButton();
        assertNotNull(addProjectButton);
        assertEquals("Add Project", addProjectButton.getText());
    }

    @Test
    public void testGetSearchProjectButton() {
        JButton searchProjectButton = switchViewButtonPanel.getSearchProjectButton();
        assertNotNull(searchProjectButton);
        assertEquals("Search Project", searchProjectButton.getText());
    }

    @Test
    public void testGetGetProjectsButton() {
        JButton getProjectsButton = switchViewButtonPanel.getGetProjectsButton();
        assertNotNull(getProjectsButton);
        assertEquals("My Projects", getProjectsButton.getText());
    }

    @Test
    public void testGetEditUserProfileButton() {
        JButton editUserProfileButton = switchViewButtonPanel.getEditUserProfileButton();
        assertNotNull(editUserProfileButton);
        assertEquals("Edit My Profile", editUserProfileButton.getText());
    }

    @Test
    public void testGetCreateUserButton() {
        JButton createUserButton = switchViewButtonPanel.getCreateUserButton();
        assertNotNull(createUserButton);
        assertEquals("Create User", createUserButton.getText());
    }

    @Test
    public void testGetLoginUserButton() {
        JButton loginUserButton = switchViewButtonPanel.getLoginUserButton();
        assertNotNull(loginUserButton);
        assertEquals("Login User", loginUserButton.getText());
    }

    @Test
    public void testGetLogoutButton() {
        JButton logoutButton = switchViewButtonPanel.getLogoutButton();
        assertNotNull(logoutButton);
        assertEquals("Logout", logoutButton.getText());
    }


    @Test
    public void testPropertyChangeLogoutSuccess() {
//        PropertyChangeEvent event = new PropertyChangeEvent(this, "logout", null, true);
//        switchViewButtonPanel.propertyChange(event);
//
//        verify(mockPlayVoiceService).playVoice("Logged out successfully");

        // not sure how to test this
    }

    @Test
    public void testPropertyChangeLogoutFail() {
//        PropertyChangeEvent event = new PropertyChangeEvent(this, "logout", null, false);
//        switchViewButtonPanel.propertyChange(event);
//
//        verify(mockPlayVoiceService).playVoice("Failed to logout");

        // not sure how to test this
    }




}
