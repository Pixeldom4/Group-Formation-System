package data_access;

import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvDataAccessObject implements DataAccessObjectInterface {
    private static final String CSV_FILE_PATH = "projects.csv";
    private char separator = '\t';
    private CSVParser parser = new CSVParserBuilder()
            .withSeparator(this.separator)
            .build();

    public List<String[]> readAll() {
        List<String[]> records = new ArrayList<>();
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(CSV_FILE_PATH))
                .withCSVParser(this.parser)
                .build()) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                records.add(nextLine);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return records;
    }

    @Override
    public String[] search(int projectId) {
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(CSV_FILE_PATH))
                .withCSVParser(this.parser)
                .build()) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (Integer.parseInt(nextLine[0]) == projectId) {
                    return nextLine;
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void write(List<String[]> records) {
        try (CSVWriter writer = (CSVWriter) new CSVWriterBuilder(new FileWriter(CSV_FILE_PATH))
                .withSeparator(this.separator)
                .build()) {
            writer.writeAll(records);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(String[] record) {
        List<String[]> records = readAll();
        records.add(record);
        write(records);
    }

    public void delete(int projectId) {
        List<String[]> records = readAll();
        records.removeIf(record -> Integer.parseInt(record[0]) == projectId);
        write(records);
    }

    public void update(String[] record) {
        List<String[]> records = readAll();
        for (int i = 0; i < records.size(); i++) {
            if (Integer.parseInt(records.get(i)[0]) == Integer.parseInt(record[0])) {
                records.set(i, record);
                break;
            }
        }
        write(records);
    }
}
