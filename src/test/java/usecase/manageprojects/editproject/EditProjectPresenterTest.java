package usecase.manageprojects.editproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.manageprojects.editproject.EditProjectOutputData;
import usecase.manageprojects.editproject.EditProjectPresenter;
import viewmodel.EditProjectPanelViewModel;

import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EditProjectPresenterTest {
    private EditProjectPresenter presenter;
    private EditProjectPanelViewModel viewModel;

    @BeforeEach
    public void setUp() {
        viewModel = mock(EditProjectPanelViewModel.class);
        presenter = new EditProjectPresenter(viewModel);
    }

    /**
     * Test the prepareSuccessView method.
     */
    @Test
    public void testSuccess() {
        EditProjectOutputData outputData = new EditProjectOutputData(1, "Project", 1000,
                                                                     "Description", new HashSet<>(List.of("Java", "Programming")));
        presenter.prepareSuccessView(outputData);
        verify(viewModel).setProjectId(1);
        verify(viewModel).setSuccess(true);
    }

    /**
     * Test the prepareFailView method.
     */
    @Test
    public void testFail() {
        presenter.prepareFailView("Error");
        verify(viewModel).setSuccess(false);
        verify(viewModel).setErrorMessage("Error");
    }
}
