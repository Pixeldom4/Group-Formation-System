package usecase.createproject;

import viewmodel.AddProjectPanelViewModel;

/**
 * Presenter class for creating projects.
 * Implements the output boundary to prepare views for project creation.
 */
public class CreateProjectPresenter implements CreateProjectOutputBoundary {
    private final AddProjectPanelViewModel addProjectPanelViewModel;

    /**
     * Constructs a CreateProjectPresenter with the specified view model.
     *
     * @param addProjectPanelViewModel the view model to update with creation results.
     */
    public CreateProjectPresenter(AddProjectPanelViewModel addProjectPanelViewModel) {
        this.addProjectPanelViewModel = addProjectPanelViewModel;
    }

    /**
     * Prepares the success view with the provided output data.
     *
     * @param outputData the output data indicating success.
     */
    @Override
    public void prepareSuccessView(CreateProjectOutputData outputData) {
        addProjectPanelViewModel.setSuccess(true);
        addProjectPanelViewModel.setProjectName(outputData.getTitle());
        addProjectPanelViewModel.firePropertyChanged();
    }

    /**
     * Prepares the failure view with the provided error message.
     *
     * @param error the error message to present in case of failure.
     */
    @Override
    public void prepareFailView(String error) {
        addProjectPanelViewModel.setSuccess(false);
        addProjectPanelViewModel.setErrorMessage(error);
        addProjectPanelViewModel.firePropertyChanged();
    }
}
