package usecase.manageapplication.createapplication.createapplication;

import org.junit.jupiter.api.Test;
import usecase.manageapplications.createapplication.CreateApplicationController;
import usecase.manageapplications.createapplication.CreateApplicationInputBoundary;

import java.io.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the CreateApplicationInputBoundary interface.
 */
public class CreateApplicationInputBoundaryTest {

    /**
     * Tests that the interactor is called with valid data.
     *
     */
    @Test
    void createApplicationWithValidDataCallsInteractor() {
        int senderUserId = 1;
        int projectId = 1;
        String text = "Application text";
        InputStream pdfInputStream = new ByteArrayInputStream(new byte[]{1, 2, 3});

        CreateApplicationInputBoundary mockInteractor = mock(CreateApplicationInputBoundary.class);
        CreateApplicationController controller = new CreateApplicationController(mockInteractor);

        controller.createApplication(senderUserId, projectId, text, pdfInputStream);

        verify(mockInteractor).createApplication(argThat(inputData ->
                inputData.getSenderUserId() == senderUserId &&
                        inputData.getProjectId() == projectId &&
                        inputData.getText().equals(text) &&
                        Arrays.equals(inputData.getPdfBytes(), new byte[]{1, 2, 3})
        ));
    }

    /**
     * Tests that an IOException is printed as an error.
     *
     * @throws IOException if an I/O error occurs
     */
    @Test
    void createApplicationWithIOExceptionPrintsError() throws IOException {
        int senderUserId = 1;
        int projectId = 1;
        String text = "Application text";
        InputStream pdfInputStream = mock(InputStream.class);
        when(pdfInputStream.readAllBytes()).thenThrow(new IOException("Test IOException"));

        CreateApplicationInputBoundary mockInteractor = mock(CreateApplicationInputBoundary.class);
        CreateApplicationController controller = new CreateApplicationController(mockInteractor);

        PrintStream originalErr = System.err;
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        controller.createApplication(senderUserId, projectId, text, pdfInputStream);

        assertTrue(errContent.toString().contains("Error reading the file: Test IOException"));

        System.setErr(originalErr);
    }
}