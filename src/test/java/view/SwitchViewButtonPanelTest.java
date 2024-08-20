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
