package usecase.manageapplications.createapplication;

/**
 * This is an immutable data structure responsible for storing the input
 *  data related to creating an application.
 */
public class CreateApplicationInputData {
    private final int senderUserId;
    private final int projectId;
    private final String text;
    private final byte[] pdfBytes;

    /**
     * Constructs a CreateApplicationInputData object.
     *
     * @param senderUserId   the ID of the user sending the application.
     * @param projectId      the ID of the project for which the application is created.
     * @param text           the application text.
     * @param pdfBytes       the bytes of the PDF file to be included in the application.
     */
    public CreateApplicationInputData(int senderUserId, int projectId, String text, byte[] pdfBytes) {
        this.senderUserId = senderUserId;
        this.projectId = projectId;
        this.text = text;
        this.pdfBytes = pdfBytes;
    }

    /**
     * Returns the user ID of the user sending the application.
     *
     * @return the user ID of the user sending the application.
     */
    public int getSenderUserId() {
        return senderUserId;
    }

    /**
     * Returns the project ID for which the application is created.
     *
     * @return the project ID for which the application is created.
     */
    public int getProjectId() {
        return projectId;
    }

    /**
     * Returns the text of the application.
     *
     * @return the text of the application.
     */
    public String getText() {
        return text;
    }

    /**
     * Returns the bytes of the PDF file to be included in the application.
     *
     * @return the bytes of the PDF file to be included in the application.
     */
    public byte[] getPdfBytes() {
        return pdfBytes;
    }
}
