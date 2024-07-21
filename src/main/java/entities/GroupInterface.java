package entities;

import java.util.HashSet;

/**
 * Interface representing a group of users associated with a project.
 */
public interface GroupInterface {

    /**
     * Returns the set of users in the group.
     *
     * @return the set of users
     */
    HashSet<User> getUsers();

    /**
     * Returns the name of the group.
     *
     * @return the name of the group
     */
    String getGroupName();

    /**
     * Returns the project associated with the group.
     *
     * @return the project
     */
    Project getProject();

    /**
     * Sets the users in the group.
     *
     * @param users the set of users to be set
     */
    void setUsers(HashSet<User> users);

    /**
     * Sets the name of the group.
     *
     * @param groupName the name of the group to be set
     */
    void setGroupName(String groupName);

    /**
     * Sets the project associated with the group.
     *
     * @param project the project to be set
     */
    void setProject(Project project);
}
