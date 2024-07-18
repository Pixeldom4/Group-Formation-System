package usecase.getprojects;

import usecase.loginuser.LoginUserOutputData;
import view.MyProjectsPanel;
import viewmodel.MyProjectsPanelViewModel;

public class GetProjectsPresenter implements GetProjectsOutputBoundary{
    private final MyProjectsPanelViewModel myProjectsPanelViewModel;

    public GetProjectsPresenter(MyProjectsPanelViewModel myProjectsPanelViewModel){
        this.myProjectsPanelViewModel = myProjectsPanelViewModel;
    }

    @Override
    public void prepareSuccessView(GetProjectsOutputData outputData) {
        myProjectsPanelViewModel.setData(outputData.getData());
    }

    @Override
    public void prepareFailView(String errorMessage) {
        myProjectsPanelViewModel.setErrorMessage(errorMessage);
    }
}
