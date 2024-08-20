package view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import usecase.manageapplications.createapplication.CreateApplicationController;
import view.services.hovervoice.IHoverVoiceService;

import javax.swing.*;
import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DisplayCreateApplicationViewTest {

    private CreateApplicationController mockController;
    private IHoverVoiceService mockHoverVoiceService;
    private DisplayCreateApplicationView displayCreateApplicationView;

    @BeforeEach
    void setUp() {
        mockController = mock(CreateApplicationController.class);
        mockHoverVoiceService = mock(IHoverVoiceService.class);

        // Create the view with the mocked controller
        displayCreateApplicationView = new DisplayCreateApplicationView(1, 2, mockController) {
            {
                this.hoverVoiceService = mockHoverVoiceService;
            }
        };
    }

    @Test
    void testFileUploadAndSubmit() throws Exception {
        // Access components directly for testing
        JTextField infoField = displayCreateApplicationView.infoField;
        JButton applicationButton = displayCreateApplicationView.applicationButton;
        JButton submitButton = displayCreateApplicationView.submitButton;
        JLabel applicationLabel = displayCreateApplicationView.applicationLabel;

        // Set some info text
        infoField.setText("Test Info");

        // Simulate the file chooser action
        File tempFile = File.createTempFile("test", ".txt");
        tempFile.deleteOnExit();
        applicationLabel.setText("Application: " + tempFile.getAbsolutePath());

        // Simulate clicking the submit button
        submitButton.doClick();

        // Capture the arguments passed to createApplication
        ArgumentCaptor<Integer> userIdCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> projectIdCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> infoCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<InputStream> inputCaptor = ArgumentCaptor.forClass(InputStream.class);

        verify(mockController, times(1)).createApplication(
                userIdCaptor.capture(),
                projectIdCaptor.capture(),
                infoCaptor.capture(),
                inputCaptor.capture()
        );

        // Verify that the correct values were passed to the controller
        assertEquals(1, userIdCaptor.getValue());
        assertEquals(2, projectIdCaptor.getValue());

        // Verify that the correct file content was passed
        try (InputStream expectedInput = new FileInputStream(tempFile);
             InputStream actualInput = inputCaptor.getValue()) {
            assertEquals(expectedInput.readAllBytes().length, actualInput.readAllBytes().length);
        }
    }

    @Test
    void testSubmitWithNoFile() {
        // Access the infoField directly for testing
        JTextField infoField = displayCreateApplicationView.infoField;
        JLabel applicationLabel = displayCreateApplicationView.applicationLabel;
        JButton submitButton = displayCreateApplicationView.submitButton;

        // Set some info text
        infoField.setText("Test Info");

        // Simulate clicking the submit button without selecting a file
        submitButton.doClick();

        // Capture the arguments passed to createApplication
        ArgumentCaptor<Integer> userIdCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> projectIdCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> infoCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<InputStream> inputCaptor = ArgumentCaptor.forClass(InputStream.class);

        verify(mockController, times(1)).createApplication(
                userIdCaptor.capture(),
                projectIdCaptor.capture(),
                infoCaptor.capture(),
                inputCaptor.capture()
        );

        // Verify that the correct values were passed to the controller
        assertEquals(1, userIdCaptor.getValue());
        assertEquals(2, projectIdCaptor.getValue());

        // Verify that an empty InputStream was passed since no file was selected
        try {
            assertEquals(0, inputCaptor.getValue().available());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
