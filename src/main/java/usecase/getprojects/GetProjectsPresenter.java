package usecase.getprojects;

import viewmodel.MyProjectsPanelViewModel;

/**
 * Presenter class for retrieving projects.
 * Implements the output boundary to prepare views for project retrieval.
 */
public class GetProjectsPresenter implements GetProjectsOutputBoundary {
    private final MyProjectsPanelViewModel myProjectsPanelViewModel;

    /**
     * Constructs a GetProjectsPresenter with the specified view model.
     *
     * @param myProjectsPanelViewModel the view model to update with retrieval results.
     */
    public GetProjectsPresenter(MyProjectsPanelViewModel myProjectsPanelViewModel) {
        this.myProjectsPanelViewModel = myProjectsPanelViewModel;
    }

    /**
     * Prepares the success view with the provided output data.
     *
     * @param outputData the output data indicating success.
     */
    @Override
    public void prepareSuccessView(GetProjectsOutputData outputData) {
        myProjectsPanelViewModel.setData(outputData.getData());
    }

    /**
     * Prepares the failure view with the provided error message.
     *
     * @param errorMessage the error message to present in case of failure.
     */
    @Override
    public void prepareFailView(String errorMessage) {
        myProjectsPanelViewModel.setErrorMessage(errorMessage);
    }
}
