package use_case.manage_project;

import data_access.CsvDataAccessObject;

public class DeleteCommonProject implements DeleteProjectInterface {
    @Override
    public void deleteProject(int projectId) {
        CsvDataAccessObject csvDataAccessObject = new CsvDataAccessObject();
        csvDataAccessObject.delete(projectId);
    }
}
