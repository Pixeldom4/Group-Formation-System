package view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import usecase.manageapplications.ManageApplicationsController;
import view.services.hovervoice.IHoverVoiceService;
import view.services.playvoice.IPlayVoiceService;
import viewmodel.DisplayProjectApplicationViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DisplayProjectApplicationViewTest {

    private DisplayProjectApplicationView displayProjectApplicationView;
    private ManageApplicationsController mockManageApplicationsController;
    private DisplayProjectApplicationViewModel mockViewModel;
    private IHoverVoiceService mockHoverVoiceService;
    private IPlayVoiceService mockPlayVoiceService;

    @BeforeEach
    void setUp() {
        mockManageApplicationsController = mock(ManageApplicationsController.class);
        mockViewModel = mock(DisplayProjectApplicationViewModel.class);
        mockHoverVoiceService = mock(IHoverVoiceService.class);
        mockPlayVoiceService = mock(IPlayVoiceService.class);

        displayProjectApplicationView = new DisplayProjectApplicationView(1, mockViewModel, mockManageApplicationsController) {
            {
                this.hoverVoiceService = mockHoverVoiceService;
                this.playVoiceService = mockPlayVoiceService;
            }
        };
    }

    @Test
    void testDisplayApplicants() {
        // Setup mock data
        Object[][] applicationsData = {
                {"Applicant 1", 1, "Applicant1.pdf", new byte[]{0, 1, 2}},
                {"Applicant 2", 2, "Applicant2.pdf", new byte[]{3, 4, 5}},
        };

        // Simulate successful application fetch
        when(mockViewModel.getApplicationData()).thenReturn(applicationsData);

        // Trigger the displayApplicants method indirectly by simulating a property change
        PropertyChangeEvent event = new PropertyChangeEvent(mockViewModel, "getAppSuccess", false, true);
        displayProjectApplicationView.propertyChange(event);

        // Verify the table was populated correctly
        JTable infoTable = displayProjectApplicationView.infoTable;
        assertEquals(2, infoTable.getRowCount());
        assertEquals("Applicant 1", infoTable.getValueAt(0, 0));
        assertEquals("view", infoTable.getValueAt(0, 1));
        assertEquals("Accept", infoTable.getValueAt(0, 2));
        assertEquals("Decline", infoTable.getValueAt(0, 3));
    }

    @Test
    void testAcceptButtonAction() {
        // Setup mock data
        Object[][] applicationsData = {
                {"Applicant 1", 1, "Applicant1.pdf", new byte[]{0, 1, 2}},
        };

        when(mockViewModel.getApplicationData()).thenReturn(applicationsData);

        // Trigger the displayApplicants method indirectly by simulating a property change
        PropertyChangeEvent event = new PropertyChangeEvent(mockViewModel, "getAppSuccess", false, true);
        displayProjectApplicationView.propertyChange(event);

        // Simulate clicking the Accept button
        displayProjectApplicationView.infoTable.getCellEditor(0, 2).stopCellEditing();
    }

    @Test
    void testDeclineButtonAction() {
        // Setup mock data
        Object[][] applicationsData = {
                {"Applicant 1", 1, "Applicant1.pdf", new byte[]{0, 1, 2}},
        };

        when(mockViewModel.getApplicationData()).thenReturn(applicationsData);

        // Trigger the displayApplicants method indirectly by simulating a property change
        PropertyChangeEvent event = new PropertyChangeEvent(mockViewModel, "getAppSuccess", false, true);
        displayProjectApplicationView.propertyChange(event);

        // Simulate clicking the Decline button
        displayProjectApplicationView.infoTable.getCellEditor(0, 3).stopCellEditing();
    }

    @Test
    void testHoverVoiceServiceInvocation() {
        // Setup mock data
        Object[][] applicationsData = {
                {"Applicant 1", 1, "Applicant1.pdf", new byte[]{0, 1, 2}},
        };

        when(mockViewModel.getApplicationData()).thenReturn(applicationsData);

        // Trigger the displayApplicants method indirectly by simulating a property change
        PropertyChangeEvent event = new PropertyChangeEvent(mockViewModel, "getAppSuccess", false, true);
        displayProjectApplicationView.propertyChange(event);

        // Verify hover voice service was called with the correct components and messages
        ArgumentCaptor<JTable> tableCaptor = ArgumentCaptor.forClass(JTable.class);
        ArgumentCaptor<Map<Point, String>> mapCaptor = ArgumentCaptor.forClass(Map.class);

        verify(mockHoverVoiceService).addTableHoverVoice(tableCaptor.capture(), mapCaptor.capture());

        // Verify the hover voice map contains the expected values
        Map<Point, String> expectedMap = new HashMap<>();
        expectedMap.put(new Point(0, 0), "Application info: Applicant 1");
        expectedMap.put(new Point(0, 1), "Press to download application");
        expectedMap.put(new Point(0, 2), "Press to accept application");
        expectedMap.put(new Point(0, 3), "Press to decline application");

        assertEquals(expectedMap, mapCaptor.getValue());
    }
}
