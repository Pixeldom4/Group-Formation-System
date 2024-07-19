package usecase.getprojects;

public class GetProjectsController {
    private final GetProjectsInteractor getProjectsInteractor;

    public GetProjectsController(GetProjectsInteractor getProjectsInteractor){
        this.getProjectsInteractor = getProjectsInteractor;
    }

    public void getProjects(GetProjectsInputData inputData) {
        getProjectsInteractor.getProjects(inputData);
    }
}
