package usecase.manageprojects.deleteproject;

/**
 * This is an immutable data structure responsible for storing the output
 * data related to deleting a project.
 *
 * @param projectId the ID of the deleted project.
 */
public record DeleteProjectOutputData(int projectId) {
}
