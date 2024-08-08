package usecase.manageusers.getuser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.manageusers.getusers.GetUsersOutputBoundary;
import usecase.manageusers.getusers.GetUsersOutputData;
import usecase.manageusers.getusers.GetUsersPresenter;
import viewmodel.MyProjectsPanelViewModel;

import static org.mockito.Mockito.*;

public class GetUsersPresenterTest {
    private GetUsersOutputBoundary presenter;
    private MyProjectsPanelViewModel viewModel;

    @BeforeEach
    public void setUp() {
        viewModel = mock(MyProjectsPanelViewModel.class);
        presenter = new GetUsersPresenter(viewModel);
    }

    @Test
    void testSuccessView() {
        GetUsersOutputData outputData = mock(GetUsersOutputData.class);
        presenter.prepareSuccessView(outputData);
        verify(viewModel, times(1)).setUsersData(outputData.getUsers());
    }

    @Test
    void testFailView() {
        presenter.prepareFailView("Error message");
        verify(viewModel, times(1)).setErrorMessage("Error message");
        verify(viewModel, times(1)).showError();
    }

}
