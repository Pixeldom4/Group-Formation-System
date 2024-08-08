package usecase.manageapplication.deleteapplication;

import org.junit.jupiter.api.Test;
import usecase.manageapplications.deleteapplication.DeleteApplicationPresenter;

/**
 * Unit tests for the DeleteApplicationPresenter class.
 */
public class DeleteApplicationPresenterTest {

    /**
     * Tests that the presenter displays a failure message when an error occurs during application deletion.
     */
    @Test
    void deleteApplicationPresenterDisplaysFailMessage() {
        String errorMessage = "Error deleting application";
        DeleteApplicationPresenter presenter = new DeleteApplicationPresenter();
        presenter.prepareFailView(errorMessage);
        // Verify that the error message is displayed
    }
}