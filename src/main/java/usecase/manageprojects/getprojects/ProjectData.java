package usecase.manageprojects.getprojects;

import java.util.HashSet;

/**
 * Data transfer object for project data.
 *
 * @param projectId          the unique identifier of the project
 * @param projectTitle       the title of the project
 * @param projectDescription the description of the project
 * @param projectBudget      the budget allocated for the project
 * @param projectTags        the tags associated with the project
 * @param isProjectOwner     the unique identifier of the project owner
 */
public record ProjectData(int projectId, String projectTitle, String projectDescription, double projectBudget,
                          HashSet<String> projectTags, boolean isProjectOwner) {
}
