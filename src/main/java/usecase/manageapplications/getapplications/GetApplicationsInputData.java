package usecase.manageapplications.getapplications;

/**
 * This is an immutable data structure responsible for storing the input
 * data related to retrieving applications.
 *
 * @param projectId the ID of the project.
 */
public record GetApplicationsInputData(int projectId) {
}
