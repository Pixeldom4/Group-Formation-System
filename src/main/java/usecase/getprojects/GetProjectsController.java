package usecase.getprojects;

import usecase.getapplications.GetApplicationsInputBoundary;

public class GetProjectsController {
    private final GetProjectsInputBoundary getProjectsInteractor;

    public GetProjectsController(GetProjectsInputBoundary getProjectsInteractor){
        this.getProjectsInteractor = getProjectsInteractor;
    }

    public void getProjects() {
        GetProjectsInputData inputData = new GetProjectsInputData();
        getProjectsInteractor.getProjects(inputData);
    }
}
