package usecase.manageprojects.getprojects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import usecase.manageprojects.getprojects.GetProjectsOutputData;
import usecase.manageprojects.getprojects.GetProjectsPresenter;
import viewmodel.MyProjectsPanelViewModel;

import static org.mockito.Mockito.*;

/**
 * Tests for the GetProjectsPresenter class.
 */
public class GetProjectsPresenterTest {

    private MyProjectsPanelViewModel myProjectsPanelViewModel;
    private GetProjectsPresenter getProjectsPresenter;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        myProjectsPanelViewModel = mock(MyProjectsPanelViewModel.class);
        getProjectsPresenter = new GetProjectsPresenter(myProjectsPanelViewModel);
    }

    /**
     * Tests the prepareSuccessView method to ensure it updates the view model with the output data.
     */
    @Test
    public void testPrepareSuccessView() {
        GetProjectsOutputData outputData = mock(GetProjectsOutputData.class);

        getProjectsPresenter.prepareSuccessView(outputData);

        verify(myProjectsPanelViewModel, times(1)).setData(outputData.getData());
    }

    /**
     * Tests the prepareFailView method to ensure it updates the view model with the error message and shows the error.
     */
    @Test
    public void testPrepareFailView() {
        String errorMessage = "Failed to retrieve projects";

        getProjectsPresenter.prepareFailView(errorMessage);

        verify(myProjectsPanelViewModel, times(1)).setErrorMessage(errorMessage);
        verify(myProjectsPanelViewModel, times(1)).showError();
    }
}
