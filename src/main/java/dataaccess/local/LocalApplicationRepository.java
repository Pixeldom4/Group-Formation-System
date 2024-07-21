package dataaccess.local;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import dataaccess.DataAccessConfig;
import dataaccess.IApplicationRepository;
import entities.Application;
import entities.ApplicationInterface;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class LocalApplicationRepository implements IApplicationRepository {

    private final String FILE_PATH;
    private final String[] header = {"sender", "projectId", "text", "pdfBytes"};
    private final HashMap<Integer, ArrayList<ApplicationInterface>> applications = new HashMap<>();

    public LocalApplicationRepository() {
        this(DataAccessConfig.getProjectCSVPath());
    }

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

    @Override
    public Application getApplication(int userId, int projectId) {
        for (ApplicationInterface application : applications.get(projectId)) {
            if (application.getSenderUserId() == userId) {
                return (Application) application;
            }
        }
        return null;
    }

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

    private String[] applicationToString(ApplicationInterface application) {
        String[] row = new String[header.length];
        row[0] = String.valueOf(application.getSenderUserId());
        row[1] = String.valueOf(application.getProjectId());
        row[2] = application.getText();
        row[3] = Arrays.toString(application.getPdfBytes());
        return row;
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
