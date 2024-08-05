package usecase.manageprojects.deleteproject;

import viewmodel.MyProjectsPanelViewModel;

/**
 * Presenter class for deleting projects.
 * Implements the output boundary to prepare views for project deletion.
 */
public class DeleteProjectPresenter implements DeleteProjectOutputBoundary {
    private final MyProjectsPanelViewModel myProjectsPanelViewModel;

    /**
     * Constructs a DeleteProjectPresenter with the specified view model.
     *
     * @param myProjectsPanelViewModel the view model to update with deletion results.
     */
    public DeleteProjectPresenter(MyProjectsPanelViewModel myProjectsPanelViewModel) {
        this.myProjectsPanelViewModel = myProjectsPanelViewModel;
    }

    /**
     * Prepares the success view with the provided output data.
     *
     * @param outputData the output data indicating success.
     */
    @Override
    public void prepareSuccessView(DeleteProjectOutputData outputData) {
        myProjectsPanelViewModel.deleteProject();
    }

    /**
     * Prepares the failure view with the provided error message.
     *
     * @param error the error message to present in case of failure.
     */
    @Override
    public void prepareFailView(String error) {
        myProjectsPanelViewModel.setErrorMessage(error);
        myProjectsPanelViewModel.showError();
    }
}
