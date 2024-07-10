package entities;

import java.util.HashSet;

public interface GroupInterface {

    HashSet<User> getUsers();
    String getGroupName();
    Project getProject();

    void setUsers(HashSet<User> users);
    void setGroupName(String groupName);
    void setProject(Project project);
}
