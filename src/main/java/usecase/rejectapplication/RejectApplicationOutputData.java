package usecase.rejectapplication;

/**
 * This is an immutable data structure responsible for storing the output
 * data related to rejecting an application.
 */
public class RejectApplicationOutputData {

    private final String rejectedName;

    /**
     * Constructs a RejectApplicationOutputData object with the specified rejected applicant's name.
     *
     * @param rejectedName the name of the rejected applicant.
     */
    public RejectApplicationOutputData(String rejectedName) {
        this.rejectedName = rejectedName;
    }

    /**
     * Gets the name of the rejected applicant.
     *
     * @return the name of the rejected applicant.
     */
    public String getRejectedName() {
        return rejectedName;
    }
}
