package usecase.getloggedinuser;

import java.util.HashSet;

public class GetLoggedInUserOutputData {

    private final int userId;
    private final String firstName;
    private final String lastName;
    private final String userEmail;
    private final double desiredCompensation;
    private final HashSet<String> tags;

    public GetLoggedInUserOutputData(int userId, String firstName, String lastName, String userEmail, double desiredCompensation, HashSet<String> tags) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userEmail = userEmail;
        this.desiredCompensation = desiredCompensation;
        this.tags = tags;
    }

    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public double getDesiredCompensation() {
        return desiredCompensation;
    }

    public HashSet<String> getTags() {
        return tags;
    }
}
