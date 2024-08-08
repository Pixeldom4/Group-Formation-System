package usecase.manageapplication.getapplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.manageapplications.getapplications.GetApplicationsOutputBoundary;
import usecase.manageapplications.getapplications.GetApplicationsOutputData;
import usecase.manageapplications.getapplications.GetApplicationsPresenter;
import viewmodel.DisplayProjectApplicationViewModel;

import static org.mockito.Mockito.*;

public class GetApplicationPresenterTest {
    private GetApplicationsOutputBoundary presenter;
    private DisplayProjectApplicationViewModel viewModel;

    @BeforeEach
    public void setUp() {
        viewModel = mock(DisplayProjectApplicationViewModel.class);
        presenter = new GetApplicationsPresenter(viewModel);
    }

    @Test
    void testSuccessView() {
        GetApplicationsOutputData outputData = mock(GetApplicationsOutputData.class);
        Object[][] empty = new Object[0][0];
        when(outputData.applications()).thenReturn(empty);
        presenter.prepareSuccessView(outputData);
        verify(viewModel, times(1)).setApplicationData(outputData.applications());
    }

    @Test
    void testFailView() {
        presenter.prepareFailView("Error message");
        verify(viewModel, times(1)).setErrorMessage("Error message");
    }
}
