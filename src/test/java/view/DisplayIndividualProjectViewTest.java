package view;

import entities.ProjectInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import view.services.hovervoice.IHoverVoiceService;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DisplayIndividualProjectViewTest {

    private IHoverVoiceService mockHoverVoiceService;
    private ProjectInterface mockProject;
    private DisplayIndividualProjectView displayIndividualProjectView;

    @BeforeEach
    void setUp() {
        mockHoverVoiceService = mock(IHoverVoiceService.class);
        mockProject = mock(ProjectInterface.class);

        // Setup mock project data
        when(mockProject.getProjectTitle()).thenReturn("Test Project Title");
        when(mockProject.getProjectId()).thenReturn(Integer.valueOf("1234"));
        when(mockProject.getProjectDescription()).thenReturn("This is a test project description.");

        // Create the view with the mocked project and hover voice service
        displayIndividualProjectView = new DisplayIndividualProjectView(mockProject) {
            {
                this.hoverVoiceService = mockHoverVoiceService;
            }
        };
    }

    @Test
    void testProjectDetailsDisplayedCorrectly() {
        // Assert that the project details are displayed correctly in the labels and text area
        JLabel projectTitleField = displayIndividualProjectView.projectTitleField;
        JLabel projectID = displayIndividualProjectView.projectID;
        JLabel tagsField = displayIndividualProjectView.tagsField;
        JTextArea projectDescriptionArea = displayIndividualProjectView.projectDescriptionArea;

        assertEquals("Project Title:Test Project Title", projectTitleField.getText());
        assertEquals("Project ID:1234", projectID.getText());
    }
}
