package usecase.createuser;

/**
 * This is an immutable data structure responsible for storing the output
 * data related to creating a user.
 */
public class CreateUserOutputData {
    private final int userId;
    private final String firstName;
    private final String lastName;
    private final String userEmail;
    private final double desiredCompensation;
    private final boolean success;

    /**
     * Constructs a CreateUserOutputData object with the specified details.
     *
     * @param userId              the ID of the created user.
     * @param firstName           the first name of the user.
     * @param lastName            the last name of the user.
     * @param userEmail           the email of the user.
     * @param desiredCompensation the desired compensation of the user.
     * @param success             whether the user creation was successful.
     */
    public CreateUserOutputData(int userId, String firstName, String lastName, String userEmail, double desiredCompensation, boolean success) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userEmail = userEmail;
        this.desiredCompensation = desiredCompensation;
        this.success = success;
    }

    /**
     * Returns the ID of the created user.
     *
     * @return the user ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Returns the first name of the user.
     *
     * @return the user's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name of the user.
     *
     * @return the user's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the email of the user.
     *
     * @return the user's email.
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * Returns the desired compensation of the user.
     *
     * @return the user's desired compensation.
     */
    public double getDesiredCompensation() {
        return desiredCompensation;
    }

    /**
     * Returns whether the user creation was successful.
     *
     * @return true if the user creation was successful, false otherwise.
     */
    public boolean isSuccess() {
        return success;
    }
}
