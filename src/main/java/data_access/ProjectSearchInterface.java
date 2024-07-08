package data_access;

import Entities.ProjectInterface;

import java.util.ArrayList;
import java.util.List;

public interface ProjectSearchInterface {
    ArrayList<ProjectInterface> searchProjects(String query, int amount);
}
