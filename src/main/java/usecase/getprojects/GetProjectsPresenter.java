package usecase.getprojects;

import usecase.loginuser.LoginUserOutputData;
import view.MyProjectsPanel;

public class GetProjectsPresenter implements GetProjectsOutputBoundary{
    private final MyProjectsPanel myProjectsPanel;

    public GetProjectsPresenter(MyProjectsPanel myProjectsPanel){
        this.myProjectsPanel = myProjectsPanel;
    }

    @Override
    public void prepareSuccessView(GetProjectsOutputData outputData) {
        myProjectsPanel.addProjects(outputData.getData());
    }

    @Override
    public void prepareFailView(String errorMessage) {

    }
}
