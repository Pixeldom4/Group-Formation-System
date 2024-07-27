package dataaccess.database.facade;

import dataaccess.IProjectRepository;
import dataaccess.database.manager.*;
import entities.Project;

import java.util.HashMap;
import java.util.HashSet;

public class ProjectFacade implements IProjectRepository {

    private final ProjectManager projectManager;
    private final ProjectTagsManager projectTagsManager;
    private final ProjectEmbeddingsManager projectEmbeddingsManager;
    private final UserProjectsManager userProjectsManager;

    public ProjectFacade(ProjectManager projectManager, ProjectTagsManager projectTagsManager, ProjectEmbeddingsManager projectEmbeddingsManager, UserProjectsManager userProjectsManager) {
        this.projectManager = projectManager;
        this.projectTagsManager = projectTagsManager;
        this.projectEmbeddingsManager = projectEmbeddingsManager;
        this.userProjectsManager = userProjectsManager;
        initialize();
    }

    private void initialize() {
        projectManager.initialize();
        projectTagsManager.initialize();
        projectEmbeddingsManager.initialize();
        userProjectsManager.initialize();
    }

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

    @Override
    public boolean deleteProject(int projectId) {
        userProjectsManager.removeProjectFromAllUsers(projectId);
        projectTagsManager.removeTags(projectId, projectTagsManager.getTagsForProject(projectId));
        projectEmbeddingsManager.removeEmbeddings(projectId);
        return projectManager.deleteProject(projectId);
    }

    @Override
    public Project getProjectById(int projectId) {
        Project project = projectManager.getProjectById(projectId);
        if (project != null) {
            HashSet<String> tags = projectTagsManager.getTagsForProject(projectId);
            project.setProjectTags(tags);
        }
        return project;
    }

    @Override
    public boolean addTags(int projectId, HashSet<String> tags) {
        return projectTagsManager.addTags(projectId, tags);
    }

    @Override
    public boolean removeTags(int projectId, HashSet<String> tags) {
        return projectTagsManager.removeTags(projectId, tags);
    }

    @Override
    public HashSet<Project> getProjectsByKeyword(String keyword) {
        return null; // Implementation not necessary for now
    }

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

    @Override
    public HashMap<Integer, float[]> getAllEmbeddings() {
        return projectEmbeddingsManager.getAllEmbeddings();
    }

    @Override
    public int getOwnerId(int projectId) {
        return projectManager.getOwnerId(projectId);
    }
}
