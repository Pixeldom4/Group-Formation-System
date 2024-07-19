package dataaccess.local;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import dataaccess.DataAccessConfig;
import dataaccess.IUserProjectsRepository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

public class LocalUserProjectsRepository implements IUserProjectsRepository {

    private final String FILE_PATH;
    private final String[] header = {"userID", "projectID"};
    private final HashMap<Integer, HashSet<Integer>> userProjects = new HashMap<>();
    private final HashMap<Integer, HashSet<Integer>> projectUsers = new HashMap<>();

    public LocalUserProjectsRepository() {
        this(DataAccessConfig.getProjectCSVPath());
    }

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
                int userId = Integer.parseInt(line[0]);
                String[] projectIds = line[1].replace("[", "").replace("]", "").split(",");
                HashSet<Integer> projects = new HashSet<Integer>();
                for (String projectId : projectIds) {
                    projects.add(Integer.parseInt(projectId));
                    projectUsers.putIfAbsent(Integer.parseInt(projectId), new HashSet<Integer>());
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

    @Override
    public boolean addUserToProject(int userId, int projectId) {
        userProjects.putIfAbsent(userId, new HashSet<Integer>());
        userProjects.get(userId).add(projectId);
        projectUsers.putIfAbsent(projectId, new HashSet<Integer>());
        projectUsers.get(projectId).add(userId);
        saveToCSV();

        return true;
    }

    @Override
    public boolean removeUserFromProject(int userId, int projectId) {
        userProjects.get(userId).remove(projectId);
        projectUsers.get(projectId).remove(userId);
        saveToCSV();

        return true;
    }

    @Override
    public boolean removeUserFromAllProjects(int userId) {
        for (int projectId : userProjects.get(userId)) {
            projectUsers.get(projectId).remove(userId);
        }
        userProjects.remove(userId);
        saveToCSV();

        return true;
    }

    @Override
    public boolean removeProjectFromAllUsers(int projectId) {
        for (int userId : projectUsers.get(projectId)) {
            userProjects.get(userId).remove(projectId);
        }
        projectUsers.remove(projectId);
        saveToCSV();

        return true;
    }

    @Override
    public HashSet<Integer> getProjectIdsForUser(int userId) {
        return userProjects.get(userId);
    }

    @Override
    public HashSet<Integer> getUserIdsForProject(int projectId) {
        return projectUsers.get(projectId);
    }
}
