package entities;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for the Application class.
 */
public class ApplicationTest {

    /**
     * Tests the getter and setter methods of the Application class.
     */
    @Test
    public void testGettersAndSetters() {
        int senderUserId = 1;
        int projectId = 101;
        String text = "This is a test application.";
        byte[] pdfBytes = new byte[]{1, 2, 3, 4, 5};

        Application application = new Application(senderUserId, projectId, text, pdfBytes);

        // Test getters
        assertEquals("The sender user ID should match", senderUserId, application.getSenderUserId());
        assertEquals("The project ID should match", projectId, application.getProjectId());
        assertEquals("The text content should match", text, application.getText());
        assertArrayEquals("The PDF bytes should match", pdfBytes, application.getPdfBytes());

        // Test setters
        int newSenderUserId = 2;
        int newProjectId = 202;
        String newText = "This is an updated test application.";
        byte[] newPdfBytes = new byte[]{5, 4, 3, 2, 1};

        application.setSenderUserId(newSenderUserId);
        application.setProjectId(newProjectId);
        application.setText(newText);
        application.setPdfBytes(newPdfBytes);

        assertEquals("The new sender user ID should match", newSenderUserId, application.getSenderUserId());
        assertEquals("The new project ID should match", newProjectId, application.getProjectId());
        assertEquals("The new text content should match", newText, application.getText());
        assertArrayEquals("The new PDF bytes should match", newPdfBytes, application.getPdfBytes());
    }

    /**
     * Tests the constructor of the Application class.
     */
    @Test
    public void testConstructor() {
        int senderUserId = 1;
        int projectId = 101;
        String text = "This is a test application.";
        byte[] pdfBytes = new byte[]{1, 2, 3, 4, 5};

        Application application = new Application(senderUserId, projectId, text, pdfBytes);

        assertNotNull("The application object should not be null", application);
        assertEquals("The sender user ID should match", senderUserId, application.getSenderUserId());
        assertEquals("The project ID should match", projectId, application.getProjectId());
        assertEquals("The text content should match", text, application.getText());
        assertArrayEquals("The PDF bytes should match", pdfBytes, application.getPdfBytes());
    }
}
