package usecase.acceptapplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.manageapplications.acceptapplication.AcceptApplicationOutputData;
import usecase.manageapplications.acceptapplication.AcceptApplicationPresenter;
import viewmodel.DisplayProjectApplicationViewModel;

import static org.mockito.Mockito.*;

public class AcceptApplicationPresenterTest {
    private AcceptApplicationPresenter acceptApplicationPresenter;
    private DisplayProjectApplicationViewModel viewModel;

    @BeforeEach
    public void setUp() {
        viewModel = mock(DisplayProjectApplicationViewModel.class);
        acceptApplicationPresenter = new AcceptApplicationPresenter(viewModel);
    }

    @Test
    void testPrepareSuccessView() {
        // Arrange
        AcceptApplicationOutputData outputData = mock(AcceptApplicationOutputData.class);
        when(outputData.getAcceptedName()).thenReturn("Accepted Name");

        // Act
        acceptApplicationPresenter.prepareSuccessView(outputData);

        // Assert
        verify(viewModel, times(1)).setSenderName("Accepted Name");
        verify(viewModel, times(1)).acceptedResult(true);
    }

    @Test
    void testPrepareFailView() {
        // Act
        acceptApplicationPresenter.prepareFailView("Error message");

        // Assert
        verify(viewModel, times(1)).setErrorMessage("Error message");
        verify(viewModel, times(1)).acceptedResult(false);
    }

}
