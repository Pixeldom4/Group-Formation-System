package usecase.createverification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CreateVerificationPresenterTest {

    private CreateVerificationViewModel viewModel;
    private CreateVerificationPresenter presenter;

    @BeforeEach
    public void setUp() {
        viewModel = mock(CreateVerificationViewModel.class);
        presenter = new CreateVerificationPresenter(viewModel);
    }

    @Test
    public void verificationCreated_withValidOutputData_callsDisplayVerificationImage() {
        CreateVerificationOutputData outputData = new CreateVerificationOutputData("validImageLocation");
        presenter.verificationCreated(outputData);
        verify(viewModel).displayVerificationImage("validImageLocation");
    }

    @Test
    public void verificationCreated_withNullOutputData_callsDisplayVerificationImageWithNull() {
        CreateVerificationOutputData outputData = new CreateVerificationOutputData(null);
        presenter.verificationCreated(outputData);
        verify(viewModel).displayVerificationImage(null);
    }

    @Test
    public void verificationCreated_withEmptyImageLocation_callsDisplayVerificationImageWithEmptyString() {
        CreateVerificationOutputData outputData = new CreateVerificationOutputData("");
        presenter.verificationCreated(outputData);
        verify(viewModel).displayVerificationImage("");
    }
}