package usecase.acceptapplication;

/**
 * This is an immutable data structure responsible for storing the output
 * data related to accepting an application.
 */
public class AcceptApplicationOutputData {
    private final String acceptedName;

    /**
     * Constructs an AcceptApplicationOutputData object with the specified accepted applicant's name.
     *
     * @param acceptedName the name of the accepted applicant.
     */
    public AcceptApplicationOutputData(String acceptedName) {
        this.acceptedName = acceptedName;
    }

    /**
     * Gets the name of the accepted applicant.
     *
     * @return the name of the accepted applicant.
     */
    public String getAcceptedName() {
        return acceptedName;
    }
}
