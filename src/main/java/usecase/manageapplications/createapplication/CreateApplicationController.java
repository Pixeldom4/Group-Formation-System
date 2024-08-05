package usecase.manageapplications.createapplication;

import java.io.IOException;
import java.io.InputStream;

/**
 * Controller class for creating applications.
 * Interacts with the input boundary to process application creation.
 */
public class CreateApplicationController {
    private final CreateApplicationInputBoundary createApplicationInteractor;

    /**
     * Constructs a CreateApplicationController.
     *
     * @param createApplicationInteractor the interactor that handles the create application use case
     */
    public CreateApplicationController(CreateApplicationInputBoundary createApplicationInteractor) {
        this.createApplicationInteractor = createApplicationInteractor;
    }

    /**
     * Creates an application with the provided input data.
     *
     * @param senderUserId   the ID of the user sending the application.
     * @param projectId      the ID of the project for which the application is created.
     * @param text           the application text.
     * @param pdfInputStream the InputStream of the PDF file to be included in the application.
     */
    public void createApplication(int senderUserId, int projectId, String text, InputStream pdfInputStream) {
        try {
            byte[] pdfBytes = pdfInputStream.readAllBytes();
            CreateApplicationInputData inputData = new CreateApplicationInputData(senderUserId, projectId, text, pdfBytes);
            createApplicationInteractor.createApplication(inputData);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
}
