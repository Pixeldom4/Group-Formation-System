package dataaccess.local;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import dataaccess.IProjectRepository;
import entities.Project;
import entities.ProjectInterface;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * Local implementation of the IProjectRepository interface.
 * Manages project data using CSV files for storage.
 */
public class LocalProjectRepository implements IProjectRepository {

    private final ILocalEmbedRepository embedDataAccess;
    private final String FILE_PATH;
    private final String[] header = {"projectId", "projectTitle", "projectBudget", "projectDescription", "projectTags", "projectOwner"};
    private final HashMap<Integer, ProjectInterface> projects = new HashMap<>();
    private final HashMap<Integer, Integer> projectOwners = new HashMap<>();
    private int maxId = 0;

    /**
     * Creates a new LocalProjectDataAccessObject with the given path as the save location.
     * Reads the projects from the CSV file if it exists.
     *
     * @param path the path to the folder of the CSV file
     */
    public LocalProjectRepository(String path, ILocalEmbedRepository embedDataAccess) {
        FILE_PATH = path + "projects.csv";
        this.embedDataAccess = embedDataAccess;
        File f = new File(FILE_PATH);
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
                HashSet<String> projectTags = Arrays.stream(line[4].replace("[", "").replace("]", "").replace("\"", "").split(",")).collect(Collectors.toCollection(HashSet::new));
                ProjectInterface project = new Project(projectId, projectTitle, projectBudget, projectDescription, projectTags);
                projectOwners.put(projectId, Integer.valueOf(line[5]));
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
     *
     * @param project The project
     * @return The string array representation
     */
    private String[] projectToString(ProjectInterface project) {
        String[] record = new String[6];
        record[0] = String.valueOf(project.getProjectId());
        record[1] = project.getProjectTitle();
        record[2] = String.valueOf(project.getProjectBudget());
        record[3] = project.getProjectDescription();
        record[4] = project.getProjectTags().toString();
        record[5] = String.valueOf(projectOwners.get(project.getProjectId()));
        return record;
    }

    /**
     * Creates a new project and saves it to the CSV file.
     *
     * @param title the title of the project
     * @param budget the budget of the project
     * @param description the description of the project
     * @param tags the tags associated with the project
     * @param embeddings the embeddings of the project
     * @param ownerId the ID of the owner of the project
     * @return the created Project object
     */
    @Override
    public Project createProject(String title,
                                 double budget,
                                 String description,
                                 HashSet<String> tags,
                                 float[] embeddings,
                                 int ownerId) {
        int projectId = maxId + 1;
        Project project = new Project(projectId, title, budget, description, tags);
        projects.put(projectId, project);
        projectOwners.put(projectId, ownerId);
        embedDataAccess.saveEmbedData(embeddings, projectId);
        saveToCSV();
        maxId++;
        return project;
    }

    /**
     * Deletes a project by its ID.
     *
     * @param projectId the ID of the project to be deleted
     * @return true if the project was successfully deleted, false otherwise
     */
    @Override
    public boolean deleteProject(int projectId) {
        if (!projects.containsKey(projectId)) {
            return false;
        }
        projects.remove(projectId);
        embedDataAccess.removeEmbedData(projectId);
        saveToCSV();
        return true;
    }

    /**
     * Retrieves a project by its ID.
     *
     * @param projectId the ID of the project to be retrieved
     * @return the Project object, or null if no project is found
     */
    @Override
    public Project getProjectById(int projectId) {
        if (projects.containsKey(projectId)) {
            return (Project) projects.get(projectId);
        }
        return null;
    }

    /**
     * Adds tags to a project.
     *
     * @param projectId the ID of the project
     * @param tags the tags to be added
     * @return true if the tags were successfully added, false otherwise
     */
    @Override
    public boolean addTags(int projectId, HashSet<String> tags) {
        ProjectInterface project = getProjectById(projectId);
        if (project == null) {
            return false;
        }
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

    /**
     * Removes tags from a project.
     *
     * @param projectId the ID of the project
     * @param tags the tags to be removed
     * @return true if the tags were successfully removed, false otherwise
     */
    @Override
    public boolean removeTags(int projectId, HashSet<String> tags) {
        ProjectInterface project = getProjectById(projectId);
        if (project == null) {
            return false;
        }
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

    /**
     * Retrieves projects by a keyword.
     *
     * @param keyword the keyword to search for
     * @return a HashSet of Project objects that match the keyword
     */
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
     * Checks if a project has a keyword in its title, description, or tags.
     *
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

    /**
     * Updates a project with new information.
     *
     * @param projectId the ID of the project to be updated
     * @param title the new title of the project
     * @param budget the new budget of the project
     * @param description the new description of the project
     * @param tags the new tags of the project
     * @param embeddings the new embeddings of the project
     * @return true if the project was successfully updated, false otherwise
     */
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

    /**
     * Retrieves all embeddings and their associated project IDs.
     *
     * @return a hashmap where the key is the project ID and the value is the embedding
     */
    @Override
    public HashMap<Integer, float[]> getAllEmbeddings() {
        return embedDataAccess.getAllEmbeddings();
    }

    /**
     * Retrieves the owner ID of a project.
     *
     * @param projectId the ID of the project
     * @return the owner ID, or 0 if no owner is found
     */
    @Override
    public int getOwnerId(int projectId) {
        if (projectOwners.containsKey(projectId)) {
            return projectOwners.get(projectId);
        }
        return 0;
    }
}
