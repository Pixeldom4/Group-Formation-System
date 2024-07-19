package usecase.manageapplications;

import entities.Application;

import java.util.HashSet;

public class ManageApplicationsOutputData {
    private final Object[][] applications;

    public ManageApplicationsOutputData(Object[][] applications) {
        this.applications = applications;
    }

    public Object[][] getApplications() {
        return applications;
    }
}
