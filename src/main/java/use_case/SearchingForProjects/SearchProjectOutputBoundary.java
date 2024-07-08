package use_case.SearchingForProjects;

import Entities.ProjectInterface;

import java.util.ArrayList;

public interface SearchProjectOutputBoundary {
    void presentProjects(ArrayList<ProjectInterface> projects);
}
