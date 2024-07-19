package usecase.createapplication;

import org.apache.commons.lang3.NotImplementedException;
import view.DisplayCreateApplicationView;

import javax.swing.*;

public class CreateApplicationPresenter implements CreateApplicationOutputBoundary {
    private final DisplayCreateApplicationView applicationView;

    public CreateApplicationPresenter(DisplayCreateApplicationView applicationView) {
        this.applicationView = applicationView;
    }
    @Override
    public void prepareSuccessView(CreateApplicationOutputData outputData) {
        applicationView.dispose();
        JOptionPane.showMessageDialog(null, "Application submitted");
    }

    @Override
    public void prepareFailView(String errorMessage) {
        JOptionPane.showMessageDialog(null, "There is a problem with the application");
    }
}
