package usecase.createapplication;

import org.apache.commons.lang3.NotImplementedException;
import view.DisplayCreateApplicationView;
import viewmodel.SearchPanelViewModel;

import javax.swing.*;

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
