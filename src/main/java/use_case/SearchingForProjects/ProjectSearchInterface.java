package use_case.SearchingForProjects;

import Entities.ProjectInterface;

import java.util.ArrayList;

public interface ProjectSearchInterface {
    ArrayList<ProjectInterface> searchProjects(String query, int amount);
}
