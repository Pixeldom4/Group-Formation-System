package data_access;

import Entities.ProjectInterface;

import java.util.List;

public interface ProjectSearchInterface {
    List<ProjectInterface> searchProjects(String query, int amount);
}
