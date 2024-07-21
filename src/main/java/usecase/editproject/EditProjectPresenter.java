package usecase.editproject;

import viewmodel.EditProjectPanelViewModel;

/**
 * Presenter class for editing projects.
 * Implements the output boundary to prepare views for project editing.
 */
public class EditProjectPresenter implements EditProjectOutputBoundary {
    private final EditProjectPanelViewModel editProjectPanelViewModel;

    /**
     * Constructs an EditProjectPresenter with the specified view model.
     *
     * @param editProjectPanelViewModel the view model to update with editing results.
     */
    public EditProjectPresenter(EditProjectPanelViewModel editProjectPanelViewModel) {
        this.editProjectPanelViewModel = editProjectPanelViewModel;
    }

    /**
     * Prepares the success view with the provided output data.
     *
     * @param outputData the output data indicating success.
     */
    @Override
    public void prepareSuccessView(EditProjectOutputData outputData) {
        editProjectPanelViewModel.setSuccess(true);
        editProjectPanelViewModel.setProjectId(outputData.getProjectId());
        editProjectPanelViewModel.setTitle(outputData.getTitle());
        editProjectPanelViewModel.setBudget(outputData.getBudget());
        editProjectPanelViewModel.setDescription(outputData.getDescription());
        editProjectPanelViewModel.setTags(outputData.getTags());
        editProjectPanelViewModel.firePropertyChanged();
    }

    /**
     * Prepares the failure view with the provided error message.
     *
     * @param error the error message to present in case of failure.
     */
    @Override
    public void prepareFailView(String error) {
        editProjectPanelViewModel.setSuccess(false);
        editProjectPanelViewModel.setErrorMessage(error);
        editProjectPanelViewModel.firePropertyChanged();
    }
}
