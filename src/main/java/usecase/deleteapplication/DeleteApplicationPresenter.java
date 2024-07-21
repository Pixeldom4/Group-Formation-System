package usecase.deleteapplication;

import javax.swing.*;

/**
 * Presenter for handling the output of the delete application use case.
 * Implements the DeleteApplicationOutputBoundary interface.
 */
public class DeleteApplicationPresenter implements DeleteApplicationOutputBoundary {

    /**
     * Constructs a new DeleteApplicationPresenter instance.
     */
    public DeleteApplicationPresenter() {

    }

    /**
     * Prepares the success view by displaying a success message.
     *
     * @param outputData the output data containing information about the deleted application
     */
    @Override
    public void prepareSuccessView(DeleteApplicationOutputData outputData) {
        JOptionPane.showMessageDialog(null, "Application deleted successfully");
    }

    /**
     * Prepares the fail view by displaying an error message.
     *
     * @param error the error message to be displayed
     */
    @Override
    public void prepareFailView(String error) {

    }
}
