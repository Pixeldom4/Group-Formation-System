package usecase.manageapplication.createapplication;

import org.junit.jupiter.api.Test;
import usecase.manageapplications.createapplication.CreateApplicationInputData;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the CreateApplicationInputData class.
 */
public class CreateApplicationInputDataTest {

    /**
     * Tests that CreateApplicationInputData stores the correct values.
     */
    @Test
    void createApplicationInputDataStoresCorrectValues() {
        int senderUserId = 1;
        int projectId = 2;
        String text = "Application text";
        byte[] pdfBytes = new byte[]{1, 2, 3};

        CreateApplicationInputData inputData = new CreateApplicationInputData(senderUserId, projectId, text, pdfBytes);

        assertEquals(senderUserId, inputData.getSenderUserId());
        assertEquals(projectId, inputData.getProjectId());
        assertEquals(text, inputData.getText());
        assertArrayEquals(pdfBytes, inputData.getPdfBytes());
    }

    /**
     * Tests that CreateApplicationInputData handles empty text correctly.
     */
    @Test
    void createApplicationInputDataHandlesEmptyText() {
        int senderUserId = 1;
        int projectId = 2;
        String text = "";
        byte[] pdfBytes = new byte[]{1, 2, 3};

        CreateApplicationInputData inputData = new CreateApplicationInputData(senderUserId, projectId, text, pdfBytes);

        assertEquals(text, inputData.getText());
    }

    /**
     * Tests that CreateApplicationInputData handles null text correctly.
     */
    @Test
    void createApplicationInputDataHandlesNullText() {
        int senderUserId = 1;
        int projectId = 2;
        byte[] pdfBytes = new byte[]{1, 2, 3};

        CreateApplicationInputData inputData = new CreateApplicationInputData(senderUserId, projectId, null, pdfBytes);

        assertNull(inputData.getText());
    }

    /**
     * Tests that CreateApplicationInputData handles empty PDF bytes correctly.
     */
    @Test
    void createApplicationInputDataHandlesEmptyPdfBytes() {
        int senderUserId = 1;
        int projectId = 2;
        String text = "Application text";
        byte[] pdfBytes = new byte[]{};

        CreateApplicationInputData inputData = new CreateApplicationInputData(senderUserId, projectId, text, pdfBytes);

        assertArrayEquals(pdfBytes, inputData.getPdfBytes());
    }

    /**
     * Tests that CreateApplicationInputData handles null PDF bytes correctly.
     */
    @Test
    void createApplicationInputDataHandlesNullPdfBytes() {
        int senderUserId = 1;
        int projectId = 2;
        String text = "Application text";

        CreateApplicationInputData inputData = new CreateApplicationInputData(senderUserId, projectId, text, null);

        assertNull(inputData.getPdfBytes());
    }
}