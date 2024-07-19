package dataaccess.local;

import dataaccess.DataAccessConfig;
import dataaccess.IProjectRepository;
import entities.Project;
import entities.ProjectInterface;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class LocalProjectRepository implements IProjectRepository {

    private EmbedDataAccessInterface embedDataAccess = DataAccessConfig.getEmbedDataAccess();
    private String FILE_PATH = DataAccessConfig.getProjectCSVPath() + "projects.csv";
    private final String[] header = {"projectId", "projectTitle", "projectBudget", "projectDescription", "projectTags"};
    private HashMap<Integer, ProjectInterface> projects = new HashMap<Integer, ProjectInterface>();
    private int maxId = 0;

    public LocalProjectRepository() {
        File f = new File(FILE_PATH);
        try {
            Files.createDirectories(f.getParentFile().toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(f.exists() && !f.isDirectory()) {
            readFromCSV();
            System.out.println("Loaded projects from " + FILE_PATH);
        }
    }

    /**
     * Creates a new LocalProjectDataAccessObject with the given path as the save location.
     * Reads the projects from the CSV file if it exists.
     * @param path the path to the folder of the CSV file
     */
    public LocalProjectRepository(String path) {
        FILE_PATH = path + "projects.csv";
        embedDataAccess = new LocalEmbedDataAccessObject(path + "embeds.csv");
        File f = new File(path);
        File parent = f.getParentFile();
        try {
            Files.createDirectories(parent.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (f.exists() && !f.isDirectory()) {
            readFromCSV();
        }
    }

    /**
     * Saves the projects to a CSV file.
     */
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

    /**
     * Reads the projects from a CSV file.
     */
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
                maxId = Math.max(maxId, projectId);
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

    /**
     * Returns a string array representation of a project. Used for CSV export.
     * @param project The project
     * @return The string array representation
     */
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
    public Project createProject(String title,
                                 double budget,
                                 String description,
                                 HashSet<String> tags,
                                 float[] embeddings) {
        int projectId = maxId + 1;
        Project project = new Project(projectId, title, budget, description, tags);
        projects.put(projectId, project);
        embedDataAccess.saveEmbedData(embeddings, projectId);
        saveToCSV();
        maxId++;
        return project;
    }


    @Override
    public boolean deleteProject(int projectId) {
        projects.remove(projectId);
        embedDataAccess.removeEmbedData(projectId);
        saveToCSV();

        return true;
    }

    @Override
    public Project getProjectById(int projectId) {
        if (projects.containsKey(projectId)) {
            return (Project) projects.get(projectId);
        }
        return null;
    }

    @Override
    public boolean addTags(int projectId, HashSet<String> tags) {
        ProjectInterface project = getProjectById(projectId);
        HashSet<String> currentTags = project.getProjectTags();
        currentTags.addAll(tags);
        update(projectId,
               project.getProjectTitle(),
               project.getProjectBudget(),
               project.getProjectDescription(),
               currentTags,
               embedDataAccess.getEmbedData(projectId));

        return true;
    }

    @Override
    public boolean removeTags(int projectId, HashSet<String> tags) {
        ProjectInterface project = getProjectById(projectId);
        HashSet<String> currentTags = project.getProjectTags();
        currentTags.removeAll(tags);
        update(projectId,
               project.getProjectTitle(),
               project.getProjectBudget(),
               project.getProjectDescription(),
               currentTags,
               embedDataAccess.getEmbedData(projectId));

        return true;
    }

    @Override
    public HashSet<Project> getProjectsByKeyword(String keyword) {
        HashSet<Project> results = new HashSet<>();
        for (ProjectInterface project : projects.values()) {
            if (projectHasKeyword(project, keyword)) {
                results.add((Project) project);
            }
        }
        return results;
    }

    /**
     * Checks if a project has a keyword in its title, description, or tags
     * @param project The project to be checked
     * @param keyword The keyword to search for
     * @return true if the project has the keyword, false otherwise
     */
    private boolean projectHasKeyword(ProjectInterface project, String keyword) {
        if (project.getProjectTitle().contains(keyword)) {
            return true;
        }
        if (project.getProjectDescription().contains(keyword)) {
            return true;
        }
        for (String tag : project.getProjectTags()) {
            if (tag.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean update(int projectId,
                       String title,
                       double budget,
                       String description,
                       HashSet<String> tags,
                       float[] embeddings) {
        ProjectInterface editProject = getProjectById(projectId);
        editProject.setProjectTitle(title);
        editProject.setProjectBudget(budget);
        editProject.setProjectDescription(description);
        editProject.setProjectTags(tags);
        embedDataAccess.saveEmbedData(embeddings, editProject.getProjectId());
        saveToCSV();

        return true;
    }

    @Override
    public HashMap<Integer, float[]> getAllEmbeddings() {
        return embedDataAccess.getAllEmbeddings();
    }
}
