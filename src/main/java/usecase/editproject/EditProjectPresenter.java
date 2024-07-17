package usecase.editproject;

import viewmodel.EditProjectPanelViewModel;

public class EditProjectPresenter implements EditProjectOutputBoundary {
    private final EditProjectPanelViewModel editProjectPanelViewModel;

    public EditProjectPresenter(EditProjectPanelViewModel editProjectPanelViewModel) {
        this.editProjectPanelViewModel = editProjectPanelViewModel;
    }

    @Override
    public void prepareSuccessView(EditProjectOutputData outputData) {
        editProjectPanelViewModel.setSuccess(true);
        editProjectPanelViewModel.setProjectId(outputData.getProjectId());
        editProjectPanelViewModel.setProjectName(outputData.getTitle());
        editProjectPanelViewModel.setBudget(outputData.getBudget());
        editProjectPanelViewModel.setDescription(outputData.getDescription());
        editProjectPanelViewModel.setTags(outputData.getTags());
        editProjectPanelViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        editProjectPanelViewModel.setSuccess(false);
        editProjectPanelViewModel.setErrorMessage(error);
        editProjectPanelViewModel.firePropertyChanged();
    }
}
