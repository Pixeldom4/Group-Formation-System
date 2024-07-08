package data_access;

import Entities.Project;
import Entities.ProjectInterface;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class LocalProjectDataAccessObject implements ProjectDataAccessInterface {

    private final EmbedDataAccessInterface embedDataAccess = DAOImplementationConfig.getEmbedDataAccess();
    private final String FILE_PATH = "local_data/projects/projects.csv";
    private final String[] header = {"projectId", "projectTitle", "projectBudget", "projectDescription", "projectTags"};
    private HashMap<Integer, ProjectInterface> projects = new HashMap<Integer, ProjectInterface>();;

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
            String[] row = projectToString(project);
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
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                int projectId = Integer.parseInt(line[0]);
                String projectTitle = line[1];
                double projectBudget = Double.parseDouble(line[2]);
                String projectDescription = line[3];
                HashSet<String> projectTags = Arrays.stream(line[4].replace("[", "").replace("]", "").split(",")).collect(Collectors.toCollection(HashSet::new));
                ProjectInterface project = new Project(projectId, projectTitle, projectBudget, projectDescription, projectTags);
                projects.put(projectId, project);
            }
        } catch (IOException | CsvValidationException e) {
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

    @Override
    public void add(String[] record) {
        ProjectInterface newProject = new Project(Integer.parseInt(record[0]), record[1], Double.parseDouble(record[2]), record[3], new HashSet<>(Arrays.asList(record[4].split(";"))));
        projects.put(Integer.parseInt(record[0]), newProject);
        saveProject(newProject);
    }

    @Override
    public void delete(int projectId) {
        projects.remove(projectId);
        embedDataAccess.removeEmbedData(projectId);
        saveToCSV();
    }

    @Override
    public void update(String[] record) {
        ProjectInterface editProject = getProject(Integer.parseInt(record[0]));
        editProject.setProjectTitle(record[1]);
        editProject.setProjectBudget(Double.parseDouble(record[2]));
        editProject.setProjectDescription(record[3]);
        editProject.setProjectTags(new HashSet<>(Arrays.asList(record[4].split(";"))));
        embedDataAccess.saveEmbedData(editProject.getProjectDescription(), editProject.getProjectId());
        saveToCSV();
    }


    @Override
    public String[] search(int projectId) {
        ProjectInterface searchProject = getProject(projectId);
        projectToString(searchProject);
        return projectToString(searchProject);
    }

    @Override
    public int numberOfProjects() {
        return projects.size();
    }

    private String[] projectToString(ProjectInterface project) {
        String[] record = new String[5];
        record[0] = String.valueOf(project.getProjectId());
        record[1] = project.getProjectTitle();
        record[2] = String.valueOf(project.getProjectBudget());
        record[3] = project.getProjectDescription();
        record[4] = project.getProjectTags().toString();
        return record;
    }
}
