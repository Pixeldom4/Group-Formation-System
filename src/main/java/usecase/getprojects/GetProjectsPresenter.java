package usecase.getprojects;

import view.MyProjectsPanel;

public class GetProjectsPresenter implements GetProjectsOutputBoundary{
    private final GetProjectsInteractor getProjectsInteractor;

    public GetProjectsPresenter(){
        getProjectsInteractor = new GetProjectsInteractor();
    }

    @Override
    public String[][] returnProjects(String[][] projects){
        return projects;
    }
}
