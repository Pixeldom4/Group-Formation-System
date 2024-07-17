package usecase.createapplication;

import dataaccess.IApplicationRepository;
import entities.Application;

/**
 * Interactor for the Create Application use case.
 * Handles the business logic for creating an application.
 */
public class CreateApplicationInteractor implements CreateApplicationInputBoundary {
    private final IApplicationRepository applicationRepository;
    private final CreateApplicationOutputBoundary applicationPresenter;

    /**
     * Constructs a CreateApplicationInteractor with the specified repository and presenter.
     *
     * @param applicationRepository the repository to interact with the database.
     * @param applicationPresenter  the presenter to handle the output presentation.
     */
    public CreateApplicationInteractor(IApplicationRepository applicationRepository, CreateApplicationOutputBoundary applicationPresenter) {
        this.applicationRepository = applicationRepository;
        this.applicationPresenter = applicationPresenter;
    }

    /**
     * Creates an application with the provided input data.
     *
     * @param inputData the input data required to create an application.
     */
    @Override
    public void createApplication(CreateApplicationInputData inputData) {
        Application application = applicationRepository.createApplication(
                inputData.getSenderUserId(),
                inputData.getProjectId(),
                inputData.getText(),
                inputData.getPdfBytes()
        );

        CreateApplicationOutputData outputData;
        if (application != null) {
            outputData = new CreateApplicationOutputData(application.getSenderUserId(), application.getProjectId(), true);
            applicationPresenter.prepareSuccessView(outputData);
        } else {
            applicationPresenter.prepareFailView("Failed to create application.");
        }
    }
}
