package data_access;

import Entities.ProjectInterface;

public interface ProjectDataAccessInterface {
    void saveProject(ProjectInterface project);
    ProjectInterface getProject(int projectId);
}
