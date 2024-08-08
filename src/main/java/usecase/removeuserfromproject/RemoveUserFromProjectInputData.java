package usecase.removeuserfromproject;

/**
 * This is an immutable data structure responsible for storing the input
 * data related to removing a user from a project.
 *
 * @param projectId the ID of the project to remove the user from.
 * @param userId    the ID of the user to remove from the project.
 * @param editorId  the ID of the user trying to remove a user from the project.
 */
public record RemoveUserFromProjectInputData(int projectId, int userId, int editorId) {
}
