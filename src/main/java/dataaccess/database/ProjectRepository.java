package dataaccess.database;

import dataaccess.IProjectRepository;
import dataaccess.database.manager.*;
import entities.Project;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Facade class that implements the IProjectRepository interface and provides methods for managing projects in the database.
 */
public class ProjectRepository implements IProjectRepository {

    private final ProjectManager projectManager;
    private final ProjectTagsManager projectTagsManager;
    private final ProjectEmbeddingsManager projectEmbeddingsManager;
    private final UserProjectsManager userProjectsManager;

    /**
     * Constructs a ProjectRepository with the specified ProjectManager, ProjectTagsManager, ProjectEmbeddingsManager, and UserProjectsManager.
     *
     * @param projectManager the ProjectManager instance.
     * @param projectTagsManager the ProjectTagsManager instance.
     * @param projectEmbeddingsManager the ProjectEmbeddingsManager instance.
     * @param userProjectsManager the UserProjectsManager instance.
     */
    public ProjectRepository(ProjectManager projectManager, ProjectTagsManager projectTagsManager, ProjectEmbeddingsManager projectEmbeddingsManager, UserProjectsManager userProjectsManager) {
        this.projectManager = projectManager;
        this.projectTagsManager = projectTagsManager;
        this.projectEmbeddingsManager = projectEmbeddingsManager;
        this.userProjectsManager = userProjectsManager;
    }


    /**
     * Creates a new project in the database.
     *
     * @param title       the title of the project.
     * @param budget      the budget of the project.
     * @param description the description of the project.
     * @param tags        the tags associated with the project.
     * @param embeddings  the embeddings associated with the project.
     * @param ownerId     the user ID of the creator of the project.
     * @return the created Project object, or null if the operation fails.
     */
    @Override
    public Project createProject(String title, double budget, String description, HashSet<String> tags, float[] embeddings, int ownerId) {
        Project project = projectManager.createProject(title, budget, description, ownerId);
        if (project != null) {
            int projectId = project.getProjectId();
            projectTagsManager.addTags(projectId, tags);
            projectEmbeddingsManager.addEmbeddings(projectId, embeddings);
            userProjectsManager.addUserToProject(ownerId, projectId);
            project.setProjectTags(tags);
        }
        return project;
    }

    /**
     * Deletes a project from the database.
     *
     * @param projectId the ID of the project.
     * @return true if the deletion was successful, false otherwise.
     */
    @Override
    public boolean deleteProject(int projectId) {
        userProjectsManager.removeProjectFromAllUsers(projectId);
        projectTagsManager.removeTags(projectId, projectTagsManager.getTagsForProject(projectId));
        projectEmbeddingsManager.removeEmbeddings(projectId);
        return projectManager.deleteProject(projectId);
    }

    /**
     * Retrieves a project from the database by ID.
     *
     * @param projectId the ID of the project.
     * @return the Project object if found, or null otherwise.
     */
    @Override
    public Project getProjectById(int projectId) {
        Project project = projectManager.getProjectById(projectId);
        if (project != null) {
            HashSet<String> tags = projectTagsManager.getTagsForProject(projectId);
            project.setProjectTags(tags);
        }
        return project;
    }

    /**
     * Adds tags to a project.
     *
     * @param projectId the ID of the project.
     * @param tags      the tags to add.
     * @return true if the tags were added successfully, false otherwise.
     */
    @Override
    public boolean addTags(int projectId, HashSet<String> tags) {
        return projectTagsManager.addTags(projectId, tags);
    }

    /**
     * Removes tags from a project.
     *
     * @param projectId the ID of the project.
     * @param tags      the tags to remove.
     * @return true if the tags were removed successfully, false otherwise.
     */
    @Override
    public boolean removeTags(int projectId, HashSet<String> tags) {
        return projectTagsManager.removeTags(projectId, tags);
    }

    /**
     * Retrieves projects that match a keyword.
     *
     * @param keyword the keyword to search for.
     * @return a HashSet of Project objects that match the keyword.
     */
    @Override
    public HashSet<Project> getProjectsByKeyword(String keyword) {
        return null; // Implementation not necessary for now
    }

    /**
     * Updates a project's information in the database.
     *
     * @param projectId   the ID of the project.
     * @param title       the title of the project.
     * @param budget      the budget of the project.
     * @param description the description of the project.
     * @param tags        the tags associated with the project.
     * @param embeddings  the embeddings associated with the project.
     * @return true if the update was successful, false otherwise.
     */
    @Override
    public boolean update(int projectId, String title, double budget, String description, HashSet<String> tags, float[] embeddings) {
        boolean isUpdated = projectManager.updateProject(projectId, title, budget, description);
        if (isUpdated) {
            projectTagsManager.removeTags(projectId, projectTagsManager.getTagsForProject(projectId));
            projectTagsManager.addTags(projectId, tags);
            projectEmbeddingsManager.removeEmbeddings(projectId);
            projectEmbeddingsManager.addEmbeddings(projectId, embeddings);
        }
        return isUpdated;
    }

    /**
     * Retrieves all project embeddings from the database.
     *
     * @return a HashMap where the keys are project IDs and the values are embeddings.
     */
    @Override
    public HashMap<Integer, float[]> getAllEmbeddings() {
        return projectEmbeddingsManager.getAllEmbeddings();
    }

    /**
     * Retrieves the owner ID of a project by its project ID.
     *
     * @param projectId the ID of the project.
     * @return the owner ID of the project, or -1 if the project is not found.
     */
    @Override
    public int getOwnerId(int projectId) {
        return projectManager.getOwnerId(projectId);
    }
}
