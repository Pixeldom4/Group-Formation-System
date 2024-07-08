package data_access;

import Entities.Project;
import Entities.User;

import java.util.HashSet;
import java.util.List;

public interface IUserRepository {
    User createUser(String email, String firstName, String lastName, HashSet<String> tags, double desiredCompensation, String password);
    User getUserByEmail(String email);

    User getUserById(int userId);

    void updateUser(User user);

    void deleteUser(int userId);
    void addTags(int userId, HashSet<String> tags);
    void removeTags(int userId, HashSet<String> tags);

    List<Project> getProjectsForUser(int userId);
}