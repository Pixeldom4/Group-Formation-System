package dataaccess.local;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import config.DataAccessConfig;
import dataaccess.IUserProjectsRepository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * Local implementation of the IUserProjectsRepository interface.
 * Manages user-project associations using CSV files for storage.
 */
public class LocalUserProjectsRepository implements IUserProjectsRepository {

    private final String FILE_PATH;
    private final String[] header = {"userID", "projectID"};
    private final HashMap<Integer, HashSet<Integer>> userProjects = new HashMap<>();
    private final HashMap<Integer, HashSet<Integer>> projectUsers = new HashMap<>();

    /**
     * Constructs a LocalUserProjectsRepository with the default file path.
     */
    public LocalUserProjectsRepository() {
        this(DataAccessConfig.getProjectCSVPath());
    }

    /**
     * Constructs a LocalUserProjectsRepository with the specified file path.
     *
     * @param path the path to the directory where the CSV file is stored
     */
    public LocalUserProjectsRepository(String path) {
        FILE_PATH = path + "userProjects.csv";
        File f = new File(FILE_PATH);
        try {
            Files.createDirectories(f.getParentFile().toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(f.exists() && !f.isDirectory()) {
            readFromCSV();
        }
    }

    /**
     * Saves the user-project associations to a CSV file.
     */
    private void saveToCSV() {
        CSVWriter writer;

        try {
            writer = new CSVWriter(new FileWriter(FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        writer.writeNext(header);
        for (int userid : userProjects.keySet()) {
            String[] row = {userid + "", userProjects.get(userid).stream().map(String::valueOf).collect(Collectors.joining(","))};
            writer.writeNext(row);
        }

        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads the user-project associations from a CSV file.
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
                int userId = Integer.parseInt(line[0]);
                String[] projectIds = line[1].replace("[", "").replace("]", "").split(",");
                HashSet<Integer> projects = new HashSet<>();
                for (String projectId : projectIds) {
                    projects.add(Integer.parseInt(projectId));
                    projectUsers.putIfAbsent(Integer.parseInt(projectId), new HashSet<>());
                    projectUsers.get(Integer.parseInt(projectId)).add(userId);
                }
                userProjects.put(userId, projects);
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
     * Adds a user to a project.
     *
     * @param userId the ID of the user
     * @param projectId the ID of the project
     * @return true if the user was successfully added to the project, false otherwise
     */
    @Override
    public boolean addUserToProject(int userId, int projectId) {
        userProjects.putIfAbsent(userId, new HashSet<>());
        userProjects.get(userId).add(projectId);
        projectUsers.putIfAbsent(projectId, new HashSet<>());
        projectUsers.get(projectId).add(userId);
        saveToCSV();

        return true;
    }

    /**
     * Removes a user from a project.
     *
     * @param userId the ID of the user
     * @param projectId the ID of the project
     * @return true if the user was successfully removed from the project, false otherwise
     */
    @Override
    public boolean removeUserFromProject(int userId, int projectId) {
        userProjects.get(userId).remove(projectId);
        projectUsers.get(projectId).remove(userId);
        saveToCSV();

        return true;
    }

    /**
     * Removes a user from all projects.
     *
     * @param userId the ID of the user
     * @return true if the user was successfully removed from all projects, false otherwise
     */
    @Override
    public boolean removeUserFromAllProjects(int userId) {
        for (int projectId : userProjects.get(userId)) {
            projectUsers.get(projectId).remove(userId);
        }
        userProjects.remove(userId);
        saveToCSV();

        return true;
    }

    /**
     * Removes a project from all users.
     *
     * @param projectId the ID of the project
     * @return true if the project was successfully removed from all users, false otherwise
     */
    @Override
    public boolean removeProjectFromAllUsers(int projectId) {
        for (int userId : projectUsers.get(projectId)) {
            userProjects.get(userId).remove(projectId);
        }
        projectUsers.remove(projectId);
        saveToCSV();

        return true;
    }

    /**
     * Retrieves the project IDs for a user.
     *
     * @param userId the ID of the user
     * @return a HashSet of project IDs for the user
     */
    @Override
    public HashSet<Integer> getProjectIdsForUser(int userId) {
        return userProjects.get(userId);
    }

    /**
     * Retrieves the user IDs for a project.
     *
     * @param projectId the ID of the project
     * @return a HashSet of user IDs for the project
     */
    @Override
    public HashSet<Integer> getUserIdsForProject(int projectId) {
        return projectUsers.get(projectId);
    }
}
