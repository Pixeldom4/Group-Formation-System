package usecase.createproject;

import viewmodel.AddProjectPanelViewModel;

public class CreateProjectPresenter implements CreateProjectOutputBoundary {
    private final AddProjectPanelViewModel addProjectPanelViewModel;

    public CreateProjectPresenter(AddProjectPanelViewModel addProjectPanelViewModel) {
        this.addProjectPanelViewModel = addProjectPanelViewModel;
    }

    @Override
    public void prepareSuccessView(CreateProjectOutputData outputData) {
        addProjectPanelViewModel.setSuccess(true);
        addProjectPanelViewModel.setProjectName(outputData.getTitle());
        addProjectPanelViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        addProjectPanelViewModel.setSuccess(false);
        addProjectPanelViewModel.setErrorMessage(error);
        addProjectPanelViewModel.firePropertyChanged();
    }
}
