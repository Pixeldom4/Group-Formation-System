package dataaccess.local;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import dataaccess.DAOImplementationConfig;
import dataaccess.IUserRepository;
import entities.User;
import entities.UserInterface;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class LocalUserRepository implements IUserRepository {
    private final String FILE_PATH;
    private final String[] header = {"userID", "userEmail", "userFirstName", "userLastName", "userTags", "userDesiredCompensation", "userPassword"};
    private final HashMap<Integer, UserInterface> users = new HashMap<Integer, UserInterface>();
    private final HashMap<Integer, String> userPasswords = new HashMap<Integer, String>();
    private int maxId = 0;

    public LocalUserRepository() {
        this(DAOImplementationConfig.getProjectCSVPath());
    }

    public LocalUserRepository(String path) {
        FILE_PATH = path + "users.csv";
        File f = new File(path);
        try {
            Files.createDirectories(f.getParentFile().toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(f.exists() && !f.isDirectory()) {
            readFromCSV();
        }
    }

    @Override
    public User createUser(String email, String firstName, String lastName, HashSet<String> tags, double desiredCompensation, String password) {
        UserInterface user = new User(maxId + 1, firstName, lastName, email, tags, desiredCompensation);
        users.put(user.getUserId(), user);
        userPasswords.put(user.getUserId(), password);
        saveToCSV();
        maxId++;
        return (User) user;
    }

    @Override
    public User getUserByEmail(String email) {
        for (UserInterface user : users.values()) {
            if (user.getUserEmail().equals(email)) {
                return (User) user;
            }
        }
        return null;
    }

    @Override
    public User getUserById(int userId) {
        if (users.containsKey(userId)) {
            return (User) users.get(userId);
        }
        return null;
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(int userId) {
        users.remove(userId);
        saveToCSV();
    }

    @Override
    public void addTags(int userId, HashSet<String> tags) {
        UserInterface user = users.get(userId);
        HashSet<String> currentTags = user.getTags();
        currentTags.addAll(tags);
        user.setTags(currentTags);
        saveToCSV();
    }

    @Override
    public void removeTags(int userId, HashSet<String> tags) {
        UserInterface user = users.get(userId);
        HashSet<String> currentTags = user.getTags();
        currentTags.removeAll(tags);
        user.setTags(currentTags);
        saveToCSV();
    }

    private String[] userToString(UserInterface user) {
        String[] row = new String[header.length];
        row[0] = String.valueOf(user.getUserId());
        row[1] = user.getUserEmail();
        row[2] = user.getFirstName();
        row[3] = user.getLastName();
        row[4] = String.valueOf(user.getTags());
        row[5] = String.valueOf(user.getDesiredCompensation());
        row[6] = userPasswords.get(user.getUserId());
        return row;
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
        for (UserInterface user : users.values()) {
            String[] row = userToString(user);
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
                String userEmail = line[1];
                String firstName = line[2];
                String lastName = line[3];
                HashSet<String> tags = new HashSet<>(Arrays.asList(line[4].replace("[", "").replace("]", "").split(",")));
                double desiredCompensation = Double.parseDouble(line[5]);
                String password = line[6];
                UserInterface user = new User(userId, firstName, userEmail, lastName, tags, desiredCompensation);
                users.put(userId, user);
                userPasswords.put(userId, password);
                maxId = Math.max(maxId, userId);
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
}
