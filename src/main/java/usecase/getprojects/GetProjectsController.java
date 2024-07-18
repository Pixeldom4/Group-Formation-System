package usecase.getprojects;

public class GetProjectsController implements GetProjectsInputBoundary {
    private final GetProjectsInteractor getProjectsInteractor;

    public GetProjectsController(GetProjectsInteractor getProjectsInteractor){
        this.getProjectsInteractor = getProjectsInteractor;
    }

    @Override
    public void getProjects(GetProjectsInputData inputData) {
        getProjectsInteractor.getProjects(inputData);
    }
}
