package data_access;

import Entities.ProjectInterface;

import java.util.List;

public interface ProjectDataAccessInterface {
    void saveProject(ProjectInterface project);
    ProjectInterface getProject(int projectId);

    void add(String[] record);
    void delete(int projectId);
    void update(String[] record);
    String[] search(int projectId);
    int numberOfProjects();
}
