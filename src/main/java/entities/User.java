package entities;

import java.util.HashSet;

public class User implements UserInterface {
    private int userId;
    private String firstName;
    private String lastName;
    private String userEmail;
    private HashSet<String> tags;
    private double desiredCompensation;
    private String password;

    // Constructor
    public User(int userId, String firstName, String lastName, String userEmail, HashSet<String> tags, double desiredCompensation, String password) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userEmail = userEmail;
        this.tags = tags;
        this.desiredCompensation = desiredCompensation;
        this.password = password;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public HashSet<String> getTags() {
        return tags;
    }

    public void setTags(HashSet<String> tags) {
        this.tags = tags;
    }

    public double getDesiredCompensation() {
        return desiredCompensation;
    }

    public void setDesiredCompensation(double desiredCompensation) {
        this.desiredCompensation = desiredCompensation;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }
}
