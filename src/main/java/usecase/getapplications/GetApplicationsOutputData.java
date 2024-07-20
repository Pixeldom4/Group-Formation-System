package usecase.getapplications;

public class GetApplicationsOutputData {
    private final Object[][] applications;

    public GetApplicationsOutputData(Object[][] applications) {
        this.applications = applications;
    }

    public Object[][] getApplications() {
        return applications;
    }
}
