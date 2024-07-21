package usecase.create_user_use_case;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.createuser.CreateUserOutputData;
import usecase.createuser.CreateUserPresenter;
import viewmodel.CreateUserPanelViewModel;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the CreateUserPresenter class.
 */
class CreateUserPresenterTest {

    private CreateUserPresenter presenter;
    private CreateUserPanelViewModel viewModel;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        viewModel = new CreateUserPanelViewModel();
        presenter = new CreateUserPresenter(viewModel);
    }

    /**
     * Tests the prepareSuccessView method with valid output data.
     */
    @Test
    void prepareSuccessViewWithValidOutputData() {
        CreateUserOutputData outputData = new CreateUserOutputData(1, "John", "Doe", "john.doe@test.com", 50000.0, null, true);

        presenter.prepareSuccessView(outputData);

        assertTrue(viewModel.isSuccess());
        assertEquals("John Doe", viewModel.getCreatedUser());
    }

    /**
     * Tests the prepareFailView method with an error message.
     */
    @Test
    void prepareFailViewWithErrorMessage() {
        String errorMessage = "Error creating user";

        presenter.prepareFailView(errorMessage);

        assertFalse(viewModel.isSuccess());
        assertEquals(errorMessage, viewModel.getErrorMessage());
    }

}
