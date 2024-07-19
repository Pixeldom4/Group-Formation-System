package usecase.deleteproject;

import javax.swing.*;

public class DeleteProjectPresenter implements DeleteProjectOutputBoundary {
    public DeleteProjectPresenter() {

    }

    @Override
    public void prepareSuccessView(DeleteProjectOutputData outputData) {
        JOptionPane.showMessageDialog(null, "Sucessfully deleted");
    }

    @Override
    public void prepareFailView(String error) {

    }
}
