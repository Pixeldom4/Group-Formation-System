package usecase.deleteproject;

import viewmodel.MyProjectsPanelViewModel;

public class DeleteProjectPresenter implements DeleteProjectOutputBoundary {
    private final MyProjectsPanelViewModel myProjectsPanelViewModel;

    public DeleteProjectPresenter(MyProjectsPanelViewModel myProjectsPanelViewModel) {
        this.myProjectsPanelViewModel = myProjectsPanelViewModel;

    }

    @Override
    public void prepareSuccessView(DeleteProjectOutputData outputData) {
        myProjectsPanelViewModel.deleteProject();
    }

    @Override
    public void prepareFailView(String error) {
        myProjectsPanelViewModel.setErrorMessage(error);
    }
}
