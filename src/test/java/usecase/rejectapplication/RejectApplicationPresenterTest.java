package usecase.rejectapplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.manageapplications.rejectapplication.RejectApplicationOutputData;
import usecase.manageapplications.rejectapplication.RejectApplicationPresenter;
import viewmodel.DisplayProjectApplicationViewModel;

import static org.mockito.Mockito.*;

public class RejectApplicationPresenterTest {
    private RejectApplicationPresenter presenter;
    private DisplayProjectApplicationViewModel viewModel;

    @BeforeEach
    public void setUp() {
        viewModel = mock(DisplayProjectApplicationViewModel.class);
        presenter = new RejectApplicationPresenter(viewModel);
    }

    @Test
    void testPrepareSuccessView() {
        RejectApplicationOutputData outputData = mock(RejectApplicationOutputData.class);
        when(outputData.getRejectedName()).thenReturn("Rejected Name");

        presenter.prepareSuccessView(outputData);

        verify(viewModel, times(1)).setSenderName("Rejected Name");
        verify(viewModel, times(1)).rejectedResult(true);
    }

    @Test
    void testPrepareFailView() {
        presenter.prepareFailView("Error message");

        verify(viewModel, times(1)).setErrorMessage("Error message");
        verify(viewModel, times(1)).rejectedResult(false);
    }
}
