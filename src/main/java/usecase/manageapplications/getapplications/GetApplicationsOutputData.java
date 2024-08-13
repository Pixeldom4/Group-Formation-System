package usecase.manageapplications.getapplications;

/**
 * This is an immutable data structure responsible for storing the output
 * data related to retrieving applications.
 *
 * @param applications the applications data.
 */
public record GetApplicationsOutputData(Object[][] applications) {
}
