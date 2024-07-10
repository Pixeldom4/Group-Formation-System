package data_access.local;

import Entities.Project;
import Entities.ProjectInterface;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import data_access.DAOImplementationConfig;
import data_access.IProjectRepository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class LocalProjectDataAccessObject implements IProjectRepository {

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

    @Override
    public Project createProject(String title, double budget, String description, HashSet<String> tags) {
        int projectId = numberOfProjects() + 1;
        Project project = new Project(projectId, title, budget, description, tags);
        projects.put(projectId, project);
        embedDataAccess.saveEmbedData(project.getProjectDescription(), projectId);
        saveToCSV();
        return project;
    }


    @Override
    public void deleteProject(int projectId) {
        projects.remove(projectId);
        embedDataAccess.removeEmbedData(projectId);
        saveToCSV();
    }

    @Override
    public Project getProjectById(int projectId) {
        if (projects.containsKey(projectId)) {
            return (Project) projects.get(projectId);
        }
        return null;
    }

    @Override
    public void addTags(int projectId, HashSet<String> tags) {
        HashSet<String> currentTags = getProjectById(projectId).getProjectTags();
        currentTags.addAll(tags);
        update(projectId, getProjectById(projectId).getProjectTitle(), getProjectById(projectId).getProjectDescription(), getProjectById(projectId).getProjectBudget(), currentTags);
    }

    @Override
    public void removeTags(int projectId, HashSet<String> tags) {

    }

    @Override
    public HashSet<Project> getProjectsByKeyword(String keyword) {
        return null;
    }

    @Override
    public void update(int projectId, String title, String description, double budget, HashSet<String> tags) {
        ProjectInterface editProject = getProjectById(projectId);
        editProject.setProjectTitle(title);
        editProject.setProjectBudget(budget);
        editProject.setProjectDescription(description);
        editProject.setProjectTags(tags);
        embedDataAccess.saveEmbedData(editProject.getProjectDescription(), editProject.getProjectId());
        saveToCSV();
    }

    @Override
    public HashMap<Integer, float[]> getAllEmbeddings() {
        return embedDataAccess.getAllEmbeddings();
    }
}
