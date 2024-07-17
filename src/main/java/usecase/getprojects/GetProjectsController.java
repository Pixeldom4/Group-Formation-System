package usecase.getprojects;

public class GetProjectsController implements GetProjectsInputBoundary {
    private final GetProjectsInteractor getProjectsInteractor;

    public GetProjectsController(){
        getProjectsInteractor = new GetProjectsInteractor();
    }

    @Override
    public void getProjects(GetProjectsInputData inputData) {
        getProjectsInteractor.getProjects(inputData);
    }
}
