package dataaccess.local;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import config.DataAccessConfig;
import dataaccess.IApplicationRepository;
import entities.Application;
import entities.ApplicationInterface;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * Local implementation of the IApplicationRepository interface.
 * Manages application data using CSV files for storage.
 */
public class LocalApplicationRepository implements IApplicationRepository {

    private final String FILE_PATH;
    private final String[] header = {"sender", "projectId", "text", "pdfBytes"};
    private final HashMap<Integer, ArrayList<ApplicationInterface>> applications = new HashMap<>();

    /**
     * Constructs a LocalApplicationRepository with the default file path.
     */
    public LocalApplicationRepository() {
        this(DataAccessConfig.getProjectCSVPath());
    }

    /**
     * Constructs a LocalApplicationRepository with the specified file path.
     *
     * @param path the path to the directory where the CSV file is stored
     */
    public LocalApplicationRepository(String path) {
        FILE_PATH = path + "applications.csv";
        File f = new File(FILE_PATH);
        try {
            Files.createDirectories(f.getParentFile().toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(f.exists() && !f.isDirectory()) {
            readFromCSV();
            System.out.println("Loaded applications from " + FILE_PATH);
        }
    }

    /**
     * Creates a new application and saves it to the CSV file.
     *
     * @param senderUserId the ID of the user sending the application
     * @param projectId the ID of the project the application is for
     * @param text the text content of the application
     * @param pdfBytes the PDF content of the application as a byte array
     * @return the created Application object, or null if an application from the same user already exists for the project
     */
    @Override
    public Application createApplication(int senderUserId, int projectId, String text, byte[] pdfBytes) {
        if (applications.get(projectId) != null) {
            for (ApplicationInterface application : applications.get(projectId)) {
                if (application.getSenderUserId() == senderUserId) {
                    return null;
                }
            }
        }
        Application application = new Application(senderUserId, projectId, text, pdfBytes);
        applications.putIfAbsent(projectId, new ArrayList<ApplicationInterface>());
        applications.get(projectId).add(application);
        saveToCSV();
        return application;
    }

    /**
     * Retrieves an application for a specific user and project.
     *
     * @param userId the ID of the user
     * @param projectId the ID of the project
     * @return the Application object, or null if no application is found
     */
    @Override
    public Application getApplication(int userId, int projectId) {
        for (ApplicationInterface application : applications.get(projectId)) {
            if (application.getSenderUserId() == userId) {
                return (Application) application;
            }
        }
        return null;
    }

    /**
     * Retrieves all applications for a specific user.
     *
     * @param userId the ID of the user
     * @return a HashSet of Application objects for the user
     */
    @Override
    public HashSet<Application> getApplicationsForUser(int userId) {
        HashSet<Application> userApplications = new HashSet<>();
        for (ArrayList<ApplicationInterface> applications : applications.values()) {
            for (ApplicationInterface application : applications) {
                if (application.getSenderUserId() == userId) {
                    userApplications.add((Application) application);
                }
            }
        }
        return userApplications;
    }

    /**
     * Retrieves all applications for a specific project.
     *
     * @param projectId the ID of the project
     * @return a HashSet of Application objects for the project, or null if no applications are found
     */
    @Override
    public HashSet<Application> getApplicationsForProject(int projectId) {
        HashSet<Application> projectApplications = new HashSet<>();
        ArrayList<ApplicationInterface> projectApplicationsList = applications.get(projectId);
        if (projectApplicationsList == null) {
            return null;
        }
        for (ApplicationInterface application : projectApplicationsList) {
            projectApplications.add((Application) application);
        }
        return projectApplications;
    }

    /**
     * Deletes an application for a specific user and project.
     *
     * @param senderUserId the ID of the user sending the application
     * @param projectId the ID of the project the application is for
     * @return true if the application was successfully deleted, false otherwise
     */
    @Override
    public boolean deleteApplication(int senderUserId, int projectId) {
        for (ApplicationInterface application : applications.get(projectId)) {
            if (application.getSenderUserId() == senderUserId) {
                applications.get(projectId).remove(application);
                saveToCSV();
                return true;
            }
        }
        return false;
    }

    /**
     * Saves the applications to a CSV file.
     */
    private void saveToCSV() {
        CSVWriter writer;

        try {
            writer = new CSVWriter(new FileWriter(FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        writer.writeNext(header);
        for (ArrayList<ApplicationInterface> applications : applications.values()) {
            for (ApplicationInterface application : applications) {
                String[] row = applicationToString(application);
                writer.writeNext(row);
            }
        }

        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Converts an ApplicationInterface object to a String array for CSV writing.
     *
     * @param application the ApplicationInterface object to convert
     * @return a String array representing the application
     */
    private String[] applicationToString(ApplicationInterface application) {
        String[] row = new String[header.length];
        row[0] = String.valueOf(application.getSenderUserId());
        row[1] = String.valueOf(application.getProjectId());
        row[2] = application.getText();
        row[3] = Arrays.toString(application.getPdfBytes());
        return row;
    }

    /**
     * Reads the applications from a CSV file.
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
                String[] row = line;
                int senderUserId = Integer.parseInt(row[0]);
                int projectId = Integer.parseInt(row[1]);
                String text = row[2];
                Byte[] readBytes = Arrays.stream(row[3].replace("[", "").replace("]", "").split(",")).map(Byte::valueOf).toArray(Byte[]::new);
                byte[] pdfBytes = new byte[readBytes.length];
                for (int i = 0 ; i < readBytes.length; i++) {
                    pdfBytes[i] = (byte) readBytes[i];
                };
                ApplicationInterface application = new Application(senderUserId, projectId, text, pdfBytes);
                applications.put(projectId, new ArrayList<ApplicationInterface>());
                applications.get(projectId).add(application);
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
