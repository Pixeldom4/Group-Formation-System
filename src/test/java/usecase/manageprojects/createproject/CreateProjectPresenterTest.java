package usecase.manageprojects.createproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import usecase.manageprojects.createproject.CreateProjectOutputData;
import usecase.manageprojects.createproject.CreateProjectPresenter;
import viewmodel.AddProjectPanelViewModel;

import java.util.Arrays;
import java.util.HashSet;

import static org.mockito.Mockito.*;

/**
 * Unit tests for the CreateProjectPresenter class.
 */
public class CreateProjectPresenterTest {

    private AddProjectPanelViewModel mockViewModel;
    private CreateProjectPresenter presenter;

    /**
     * Sets up the test environment before each test.
     * Initializes the mock objects and the presenter instance.
     */
    @BeforeEach
    void setUp() {
        mockViewModel = mock(AddProjectPanelViewModel.class);
        presenter = new CreateProjectPresenter(mockViewModel);
    }

    /**
     * Tests the prepareSuccessView method.
     * Verifies that the view model is updated correctly when the project creation is successful.
     */
    @Test
    void prepareSuccessViewUpdatesViewModelCorrectly() {
        CreateProjectOutputData outputData = new CreateProjectOutputData(1, "Project Title", 1000.0, "Description", new HashSet<>(Arrays.asList("tag1", "tag2")));

        presenter.prepareSuccessView(outputData);

        verify(mockViewModel).setSuccess(true);
        verify(mockViewModel).setProjectName("Project Title");
        verify(mockViewModel).firePropertyChanged();
    }

    /**
     * Tests the prepareFailView method.
     * Verifies that the view model is updated correctly when the project creation fails.
     */
    @Test
    void prepareFailViewUpdatesViewModelCorrectly() {
        String errorMessage = "Failed to create project.";

        presenter.prepareFailView(errorMessage);

        verify(mockViewModel).setSuccess(false);
        verify(mockViewModel).setErrorMessage(errorMessage);
        verify(mockViewModel).firePropertyChanged();
    }
}