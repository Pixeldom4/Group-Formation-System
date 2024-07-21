package view;

import viewmodel.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Manages the different views in the application.
 */
public class ViewManager implements PropertyChangeListener {
    private final CardLayout cardLayout;
    private final JPanel views;
    private final ViewManagerModel viewManagerModel;

    /**
     * Creates a new view manager.
     *
     * @param views the panels that the view manager will manage
     * @param layout the layout where the panels will be displayed
     * @param viewManagerModel the view manager model that controls the view
     */
    public ViewManager(JPanel views, CardLayout layout, ViewManagerModel viewManagerModel) {
        this.views = views;
        this.cardLayout = layout;
        this.viewManagerModel = viewManagerModel;
        this.viewManagerModel.addPropertyChangeListener(this);
    }

    /**
     * Handles property change events to switch views.
     *
     * @param evt the property change event
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("view")) {
            String viewModelName = (String) evt.getNewValue();
            cardLayout.show(views, viewModelName);
        }
    }
}
