package use_case.manage_project;

import Entities.Project;
import data_access.CsvDataAccessObject;

import java.util.Arrays;
import java.util.HashSet;

public class SearchCommonProject implements SearchProjectInterface {
    @Override
    public Project searchProject(int projectId) {
        CsvDataAccessObject csvDataAccessObject = new CsvDataAccessObject();
        String[] record = csvDataAccessObject.search(projectId);
        String projectTitle = record[1];
        double projectBudget = Double.valueOf(record[2]);
        String projectDescription = record[3];
        HashSet<String> projectTags = new HashSet(Arrays.asList(record[4].split(";")));

        Project project = new Project(projectId, projectTitle, projectBudget, projectDescription, projectTags);

        return project;
    }
}
