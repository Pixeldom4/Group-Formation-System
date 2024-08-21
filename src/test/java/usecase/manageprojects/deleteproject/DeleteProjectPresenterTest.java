package usecase.manageprojects.deleteproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import viewmodel.MyProjectsPanelViewModel;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DeleteProjectPresenterTest {
    private DeleteProjectPresenter presenter;
    private MyProjectsPanelViewModel viewModel;

    @BeforeEach
    void setUp() {
        viewModel = mock(MyProjectsPanelViewModel.class);
        presenter = new DeleteProjectPresenter(viewModel);
    }

    @Test
    void testPrepareSuccessView() {
        DeleteProjectOutputData outputData = new DeleteProjectOutputData(10);
        presenter.prepareSuccessView(outputData);

        verify(viewModel).deleteProject();
    }

    @Test
    void testPrepareFailView() {
        presenter.prepareFailView("Error message");

        verify(viewModel).setErrorMessage("Error message");
        verify(viewModel).showError();
    }
}
