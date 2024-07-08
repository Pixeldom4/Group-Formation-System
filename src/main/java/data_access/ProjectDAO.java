package data_access;
import Entities.Project;

import java.util.ArrayList;
import java.util.List;

public interface ProjectDAO {
    List<Project> MatchedProjects(String keywords);
}


