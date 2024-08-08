package usecase.manageapplication.createapplication.createapplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import viewmodel.SearchPanelViewModel;
import usecase.manageapplications.createapplication.CreateApplicationOutputData;
import usecase.manageapplications.createapplication.CreateApplicationPresenter;

import static org.mockito.Mockito.verify;

/**
 * Unit tests for the CreateApplicationPresenter class.
 */
public class CreateApplicationPresenterTest {

    private SearchPanelViewModel mockViewModel;
    private CreateApplicationPresenter presenter;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        mockViewModel = Mockito.mock(SearchPanelViewModel.class);
        presenter = new CreateApplicationPresenter(mockViewModel);
    }

    /**
     * Tests that the view model is updated correctly when the application creation is successful.
     */
    @Test
    void prepareSuccessViewUpdatesViewModel() {
        CreateApplicationOutputData outputData = new CreateApplicationOutputData(1, 2, true);
        presenter.prepareSuccessView(outputData);
        verify(mockViewModel).successApplication();
    }

    /**
     * Tests that the view model is updated with an error message when the application creation fails.
     */
    @Test
    void prepareFailViewUpdatesViewModelWithErrorMessage() {
        String errorMessage = "Error occurred";
        presenter.prepareFailView(errorMessage);
        verify(mockViewModel).errorApplication(errorMessage);
    }
}