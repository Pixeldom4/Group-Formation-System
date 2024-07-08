package use_case.manage_project;

import Entities.Project;
import data_access.CsvDataAccessObject;

public class CreateCommonProject implements CreateProjectInterface {
    private String[] record = new String[5];

    @Override
    public void createProject(Project project) {
        this.record[0] = String.valueOf(project.getProjectId());
        this.record[1] = project.getProjectTitle();
        this.record[2] = String.valueOf(project.getProjectBudget());
        this.record[3] = project.getProjectDescription();
        this.record[4] = String.join(";", project.getProjectTags());

        CsvDataAccessObject csvDataAccessObject = new CsvDataAccessObject();
        csvDataAccessObject.add(this.record);
    }
}
