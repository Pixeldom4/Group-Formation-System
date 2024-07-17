package entities;

/**
 * Interface for the Application entity.
 */
public interface ApplicationInterface {

    /**
     * Gets the sender's user ID.
     *
     * @return the sender's user ID.
     */
    int getSenderUserId();

    /**
     * Sets the sender's user ID.
     *
     * @param senderUserId the sender's user ID.
     */
    void setSenderUserId(int senderUserId);

    /**
     * Gets the project ID.
     *
     * @return the project ID.
     */
    int getProjectId();

    /**
     * Sets the project ID.
     *
     * @param projectId the project ID.
     */
    void setProjectId(int projectId);

    /**
     * Gets the text content of the application.
     *
     * @return the text content of the application.
     */
    String getText();

    /**
     * Sets the text content of the application.
     *
     * @param text the text content of the application.
     */
    void setText(String text);

    /**
     * Gets the PDF file as a byte array.
     *
     * @return the PDF file as a byte array.
     */
    byte[] getPdfBytes();

    /**
     * Sets the PDF file as a byte array.
     *
     * @param pdfBytes the PDF file as a byte array.
     */
    void setPdfBytes(byte[] pdfBytes);
}
