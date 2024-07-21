package usecase.createapplication;

import viewmodel.SearchPanelViewModel;

public class CreateApplicationPresenter implements CreateApplicationOutputBoundary {
    private final SearchPanelViewModel searchPanelViewModel;

    public CreateApplicationPresenter(SearchPanelViewModel searchPanelViewModel) {
        this.searchPanelViewModel = searchPanelViewModel;
    }

    @Override
    public void prepareSuccessView(CreateApplicationOutputData outputData) {
        searchPanelViewModel.successApplication();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        searchPanelViewModel.errorApplication(errorMessage);
    }
}
