package usecase.edituser;

/**
 * This is an immutable data structure responsible for storing the input
 * data related to edit a user profile.
 */
public class EditUserInputData {
    private String FirstName;
    private String LastName;
    private double DesiredCompensation;


    /**
     * Edits a CreateProjectInputData object with the specified details.
     *
     * @param FirstName      the first name of the user.
     * @param LastName      the last name of the user.
     * @param DesiredCompensation the desired compensation of the user.
     */
    public EditUserInputData(String FirstName, String LastName, double DesiredCompensation) {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.DesiredCompensation = DesiredCompensation;
    }

    /**.
     * @return the first name of the user.
     */
    public String getFirstName() {
        return FirstName;
    }

    /**.
     * @return the last name of the user.
     */
    public String getLastName() {
        return LastName;
    }

    /**.
     * @return the desired compensation of the user.
     */
    public double getDesiredCompensation() {
        return DesiredCompensation;
    }

}
