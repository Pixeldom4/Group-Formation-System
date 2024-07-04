package Entities;

import java.util.HashSet;

public class Group {
    private HashSet<User> users;
    private String groupName;
    private Project project;

    // Constructor
    public Group(HashSet<User> users, String groupName, Project project) {
        this.users = users;
        this.groupName = groupName;
        this.project = project;
    }

    // Getters and Setters
    public HashSet<User> getUsers() {
        return users;
    }

    public void setUsers(HashSet<User> users) {
        this.users = users;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
