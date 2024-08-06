package usecase.getusers;

import viewmodel.MyProjectsPanelViewModel;

public class GetUsersPresenter implements GetUsersOutputBoundary {
    private final MyProjectsPanelViewModel viewModel;

    public GetUsersPresenter(MyProjectsPanelViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(GetUsersOutputData outputData) {
        viewModel.setUsersData(outputData.getUsers());
    }

    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.setErrorMessage(errorMessage);
        viewModel.showError();
    }
}
