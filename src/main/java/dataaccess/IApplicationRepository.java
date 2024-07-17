package dataaccess;

import entities.Application;

import java.util.HashSet;

/**
 * Interface for managing Application entities.
 */
public interface IApplicationRepository {

    /**
     * Creates a new application in the database.
     *
     * @param senderUserId the ID of the sender user.
     * @param projectId    the ID of the project.
     * @param text         the application text.
     * @param pdfBytes     the PDF bytes of the application.
     * @return the created Application object, or null if the operation fails.
     */
    Application createApplication(int senderUserId, int projectId, String text, byte[] pdfBytes);

    /**
     * Retrieves an application from the database based on the user ID and project ID.
     *
     * @param userId    the ID of the user.
     * @param projectId the ID of the project.
     * @return the Application object if found, or null otherwise.
     */
    Application getApplication(int userId, int projectId);

    /**
     * Retrieves all applications for a specific user.
     *
     * @param userId the ID of the user.
     * @return a HashSet of Application objects for the specified user.
     */
    HashSet<Application> getApplicationsForUser(int userId);

    /**
     * Retrieves all applications for a specific project.
     *
     * @param projectId the ID of the project.
     * @return a HashSet of Application objects for the specified project.
     */
    HashSet<Application> getApplicationsForProject(int projectId);

    /**
     * Deletes an application from the database based on the sender user ID and project ID.
     *
     * @param senderUserId the ID of the sender user.
     * @param projectId    the ID of the project.
     * @return true if the application was successfully deleted, false otherwise.
     */
    boolean deleteApplication(int senderUserId, int projectId);
}