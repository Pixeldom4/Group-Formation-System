package data_access;

import Entities.Project;

import java.util.HashMap;
import java.util.HashSet;

public interface IProjectRepository {
    Project createProject(String title, double budget, String description, HashSet<String> tags);

    void deleteProject(int projectId);

    Project getProjectById(int projectId);

    void addTags(int projectId, HashSet<String> tags);
    void removeTags(int projectId, HashSet<String> tags);

    HashSet<Project> getProjectsByKeyword(String keyword);

    void update(int projectId, String title, String description, double budget, HashSet<String> tags);

    HashMap<Integer, float[]> getAllEmbeddings();
}