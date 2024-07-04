package Entities;

import java.util.ArrayList;

public class User {
    private int userId;
    private String userName;
    private String userEmail;
    private ArrayList<String> skills;
    private double desiredCompensation;

    // Constructor
    public User(int userId, String userName, String userEmail, ArrayList<String> skills, double desiredCompensation) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.skills = skills;
        this.desiredCompensation = desiredCompensation;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    public double getDesiredCompensation() {
        return desiredCompensation;
    }

    public void setDesiredCompensation(double desiredCompensation) {
        this.desiredCompensation = desiredCompensation;
    }
}
