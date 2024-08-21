package usecase.manageprojects.deleteproject;

/**
 * This is an immutable data structure responsible for storing the input
 * data related to deleting a project.
 *
 * @param projectId the ID of the project to be deleted.
 */
public record DeleteProjectInputData(int projectId) {
}
