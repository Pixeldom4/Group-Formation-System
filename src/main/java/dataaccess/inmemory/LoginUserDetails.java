package dataaccess.inmemory;

import dataaccess.ILoginUserDetails;

import java.util.HashSet;

public class LoginUserDetails implements ILoginUserDetails {
    private int userId;
    private String userEmail;
    private String firstName;
    private String lastName;
    private double desiredCompensation;
    private HashSet<String> tags;
    private boolean isLoggedIn;

    public LoginUserDetails(){
        this.isLoggedIn = false;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public String getUserEmail() {
        return userEmail;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public double getDesiredCompensation() {
        return desiredCompensation;
    }

    @Override
    public HashSet<String> getTags() {
        return tags;
    }

    @Override
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    @Override
    public void logout() {
        userId = 0;
        userEmail = null;
        firstName = null;
        lastName = null;
        desiredCompensation = 0;
        tags = null;
        isLoggedIn = false;
    }

    @Override
    public void login(int userId, String userEmail, String firstName, String lastName, double desiredCompensation, HashSet<String> tags) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.desiredCompensation = desiredCompensation;
        this.tags = tags;
        this.isLoggedIn = true;
    }
}
