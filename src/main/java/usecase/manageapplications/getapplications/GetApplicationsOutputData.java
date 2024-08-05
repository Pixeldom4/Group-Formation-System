package usecase.manageapplications.getapplications;

/**
 * This is an immutable data structure responsible for storing the output
 * data related to retrieving applications.
 */
public class GetApplicationsOutputData {
    private final Object[][] applications;

    /**
     * Constructs a GetApplicationsOutputData object with the specified applications data.
     *
     * @param applications the applications data.
     */
    public GetApplicationsOutputData(Object[][] applications) {
        this.applications = applications;
    }

    /**
     * Gets the applications data.
     *
     * @return the applications data.
     */
    public Object[][] getApplications() {
        return applications;
    }
}
