package use_case.manage_project;

import Entities.Project;
import data_access.DAOImplementationConfig;
import data_access.IProjectRepository;

import java.util.Arrays;
import java.util.HashSet;

public class UpdateCommonProject implements UpdateProjectInterface {
    private String[] record = new String[5];
    private IProjectRepository projectDAO = DAOImplementationConfig.getProjectDataAccess();

    @Override
    public void updateProject(Project project) {
        this.record[0] = String.valueOf(project.getProjectId());
        this.record[1] = project.getProjectTitle();
        this.record[2] = String.valueOf(project.getProjectBudget());
        this.record[3] = project.getProjectDescription();
        this.record[4] = String.join(";", project.getProjectTags());
        float[] embeddings = projectDAO.getAllEmbeddings().get(project.getProjectId());

        IProjectRepository csvDataAccessObject = DAOImplementationConfig.getProjectDataAccess();
        csvDataAccessObject.update(Integer.parseInt(record[0]),
                                   record[1],
                                   Double.parseDouble(record[2]),
                                   record[3],
                                   new HashSet<>(Arrays.asList(record[4].split(";"))),
                                   embeddings);
    }
}
