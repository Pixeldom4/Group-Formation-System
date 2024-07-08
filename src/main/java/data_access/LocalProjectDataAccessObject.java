package data_access;

import Entities.Project;
import Entities.ProjectInterface;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.spotify.voyager.jni.Index;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.HashSet;

public class LocalProjectDataAccessObject implements ProjectDataAccessInterface {

    private final String FILE_PATH = "local_data/projects/projects.csv";
    private final String[] header = {"projectId", "projectTitle", "projectBudget", "projectDescription", "projectTags"};
    private HashMap<Integer, ProjectInterface> projects = new HashMap<Integer, ProjectInterface>();
    private EmbedDataAccessInterface embedDataAccess = new LocalEmbedDataAccessObject();

    public LocalProjectDataAccessObject() {
        File f = new File(FILE_PATH);
        if(f.exists() && !f.isDirectory()) {
            readFromCSV();
        }
    }

    @Override
    public void saveProject(ProjectInterface project) {
        projects.put(project.getProjectId(), project);
        embedDataAccess.saveEmbedData(project.getProjectDescription(), project.getProjectId());
        saveToCSV();
    }

    private void saveToCSV() {
        CSVWriter writer;

        try {
            writer = new CSVWriter(new FileWriter(FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        writer.writeNext(header);
        for (ProjectInterface project : projects.values()) {
            String[] row = new String[5];
            row[0] = String.valueOf(project.getProjectId());
            row[1] = project.getProjectTitle();
            row[2] = String.valueOf(project.getProjectBudget());
            row[3] = project.getProjectDescription();
            row[4] = project.getProjectTags().toString();
            writer.writeNext(row);
        }

        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromCSV() {
        CSVReader reader;

        try {
            reader = new CSVReader(new FileReader(FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] line;
        try {
            while ((line = reader.readNext()) != null) {
                int projectId = Integer.parseInt(line[0]);
                String projectTitle = line[1];
                double projectBudget = Double.parseDouble(line[2]);
                String projectDescription = line[3];
                HashSet<String> projectTags = Arrays.stream(line[4].replace("[", "").replace("]", "").split(",")).collect(Collectors.toCollection(HashSet::new));
                ProjectInterface project = new Project(projectId, projectTitle, projectBudget, projectDescription, projectTags);
                projects.put(projectId, project);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ProjectInterface getProject(int projectId) {
        if (projects.containsKey(projectId)) {
            return projects.get(projectId);
        }
        return null;
    }
}
