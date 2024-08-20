package view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import viewmodel.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;

import static org.mockito.Mockito.*;

public class ViewManagerTest {

    private ViewManager viewManager;
    private CardLayout mockCardLayout;
    private JPanel mockViewsPanel;
    private ViewManagerModel mockViewManagerModel;

    @Before
    public void setUp() {
        mockCardLayout = Mockito.mock(CardLayout.class);
        mockViewsPanel = Mockito.mock(JPanel.class);
        mockViewManagerModel = Mockito.mock(ViewManagerModel.class);

        viewManager = new ViewManager(mockViewsPanel, mockCardLayout, mockViewManagerModel);
    }

    @Test
    public void testViewChange() {
        // Simulate a property change event
        PropertyChangeEvent event = new PropertyChangeEvent(mockViewManagerModel, "view", null, "NewView");

        // Trigger the property change event
        viewManager.propertyChange(event);

        // Verify that the CardLayout's show method was called with the correct view name
        verify(mockCardLayout).show(mockViewsPanel, "NewView");
    }

    @Test
    public void testNoViewChangeForOtherProperties() {
        // Simulate a property change event for a different property
        PropertyChangeEvent event = new PropertyChangeEvent(mockViewManagerModel, "otherProperty", null, "SomeValue");

        // Trigger the property change event
        viewManager.propertyChange(event);

        // Verify that the CardLayout's show method was NOT called
        verify(mockCardLayout, never()).show(any(JPanel.class), anyString());
    }

    @Test
    public void testPropertyChangeListenerIsAdded() {
        // Verify that the ViewManagerModel had a PropertyChangeListener added
        verify(mockViewManagerModel).addPropertyChangeListener(viewManager);
    }
}
