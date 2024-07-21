package usecase.createapplication;

import viewmodel.SearchPanelViewModel;

/**
 * Presenter class for creating applications.
 * Implements the output boundary to prepare views for application creation.
 */
public class CreateApplicationPresenter implements CreateApplicationOutputBoundary {
    private final SearchPanelViewModel searchPanelViewModel;

    /**
     * Constructs a CreateApplicationPresenter with the specified view model.
     *
     * @param searchPanelViewModel the view model to update with creation results.
     */
    public CreateApplicationPresenter(SearchPanelViewModel searchPanelViewModel) {
        this.searchPanelViewModel = searchPanelViewModel;
    }

    /**
     * Prepares the success view with the provided output data.
     *
     * @param outputData the output data indicating success.
     */
    @Override
    public void prepareSuccessView(CreateApplicationOutputData outputData) {
        searchPanelViewModel.successApplication();
    }

    /**
     * Prepares the failure view with the provided error message.
     *
     * @param errorMessage the error message to present in case of failure.
     */
    @Override
    public void prepareFailView(String errorMessage) {
        searchPanelViewModel.errorApplication(errorMessage);
    }
}
