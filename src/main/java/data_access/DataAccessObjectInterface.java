package data_access;

import java.util.List;

public interface DataAccessObjectInterface {
    String[] search(int projectId);
    void write(List<String[]> records);
}
