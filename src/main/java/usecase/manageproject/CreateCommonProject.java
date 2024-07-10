package usecase.manageproject;

import entities.Project;
import api.EmbeddingAPIInterface;
import api.OpenAPIDataEmbed;
import data_access.DAOImplementationConfig;
import data_access.IProjectRepository;

import java.util.Arrays;
import java.util.HashSet;

public class CreateCommonProject implements CreateProjectInterface {
    private String[] record = new String[5];
    private EmbeddingAPIInterface apiInteface = new OpenAPIDataEmbed();

    @Override
    public void createProject(Project project) {
        this.record[1] = project.getProjectTitle();
        this.record[2] = String.valueOf(project.getProjectBudget());
        this.record[3] = project.getProjectDescription();
        this.record[4] = String.join(";", project.getProjectTags());
        float[] embedding = apiInteface.getEmbedData(project.getProjectDescription());

        IProjectRepository csvDataAccessObject = DAOImplementationConfig.getProjectDataAccess();
        csvDataAccessObject.createProject(record[1], Double.parseDouble(record[2]), record[3], new HashSet<>(Arrays.asList(record[4].split(";"))), embedding);
    }
}
