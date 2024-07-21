package entities;

import java.util.HashSet;

/**
 * Represents a group of users associated with a project.
 */
public class Group implements GroupInterface {
    private HashSet<User> users;
    private String groupName;
    private Project project;

    /**
     * Constructs a Group with the specified users, group name, and project.
     *
     * @param users the set of users in the group
     * @param groupName the name of the group
     * @param project the project associated with the group
     */
    public Group(HashSet<User> users, String groupName, Project project) {
        this.users = users;
        this.groupName = groupName;
        this.project = project;
    }

    /**
     * Returns the set of users in the group.
     *
     * @return the set of users
     */
    public HashSet<User> getUsers() {
        return users;
    }

    /**
     * Sets the users in the group.
     *
     * @param users the set of users to be set
     */
    public void setUsers(HashSet<User> users) {
        this.users = users;
    }

    /**
     * Returns the name of the group.
     *
     * @return the name of the group
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Sets the name of the group.
     *
     * @param groupName the name of the group to be set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * Returns the project associated with the group.
     *
     * @return the project
     */
    public Project getProject() {
        return project;
    }

    /**
     * Sets the project associated with the group.
     *
     * @param project the project to be set
     */
    public void setProject(Project project) {
        this.project = project;
    }
}
