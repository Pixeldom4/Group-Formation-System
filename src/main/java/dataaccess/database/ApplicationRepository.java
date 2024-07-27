package dataaccess.database;

import dataaccess.IApplicationRepository;
import dataaccess.database.manager.ApplicationManager;
import entities.Application;

import java.util.HashSet;

/**
 * Facade class that implements the IApplicationRepository interface and provides methods for managing applications in the database.
 */
public class ApplicationRepository implements IApplicationRepository {

    private final ApplicationManager applicationManager;

    /**
     * Constructs an ApplicationRepository with the specified ApplicationManager.
     *
     * @param applicationManager the ApplicationManager instance.
     */
    public ApplicationRepository(ApplicationManager applicationManager) {
        this.applicationManager = applicationManager;
    }


    /**
     * Creates a new application in the database.
     *
     * @param senderUserId the ID of the sender user.
     * @param projectId    the ID of the project.
     * @param text         the application text.
     * @param pdfBytes     the PDF bytes of the application.
     * @return the created Application object, or null if the operation fails.
     */
    @Override
    public Application createApplication(int senderUserId, int projectId, String text, byte[] pdfBytes) {
        return applicationManager.createApplication(senderUserId, projectId, text, pdfBytes);
    }

    /**
     * Retrieves an application from the database by user ID and project ID.
     *
     * @param userId    the ID of the user.
     * @param projectId the ID of the project.
     * @return the Application object if found, or null otherwise.
     */
    @Override
    public Application getApplication(int userId, int projectId) {
        return applicationManager.getApplication(userId, projectId);
    }

    /**
     * Retrieves all applications for a specific user.
     *
     * @param userId the ID of the user.
     * @return a HashSet of Application objects for the specified user.
     */
    @Override
    public HashSet<Application> getApplicationsForUser(int userId) {
        return applicationManager.getApplicationsForUser(userId);
    }

    /**
     * Retrieves all applications for a specific project.
     *
     * @param projectId the ID of the project.
     * @return a HashSet of Application objects for the specified project.
     */
    @Override
    public HashSet<Application> getApplicationsForProject(int projectId) {
        return applicationManager.getApplicationsForProject(projectId);
    }

    /**
     * Deletes an application from the database by sender user ID and project ID.
     *
     * @param senderUserId the ID of the sender user.
     * @param projectId    the ID of the project.
     * @return true if the application was successfully deleted, false otherwise.
     */
    @Override
    public boolean deleteApplication(int senderUserId, int projectId) {
        return applicationManager.deleteApplication(senderUserId, projectId);
    }
}
