package usecase.edituser;

import java.util.HashSet;

public class EditUserOutputData {
    private final int userId;
    private final String firstName;
    private final String lastName;
    private final double desiredCompensation;
    private final HashSet<String> tags;

    public EditUserOutputData(int userId, String firstName, String lastName, double desiredCompensation, HashSet<String> tags) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public double getDesiredCompensation() {
        return desiredCompensation;
    }

    public HashSet<String> getTags() {
        return tags;
    }
}
