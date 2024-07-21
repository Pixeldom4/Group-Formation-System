package usecase.createapplication;

import dataaccess.IApplicationRepository;
import dataaccess.IUserProjectsRepository;
import entities.Application;

/**
 * Interactor for the Create Application use case.
 * Handles the business logic for creating an application.
 */
public class CreateApplicationInteractor implements CreateApplicationInputBoundary {
    private final IApplicationRepository applicationRepository;
    private final CreateApplicationOutputBoundary applicationPresenter;
    private final IUserProjectsRepository userProjectsRepository;

    /**
     * Constructs a CreateApplicationInteractor with the specified repository and presenter.
     *
     * @param applicationRepository the repository to interact with the database.
     * @param applicationPresenter  the presenter to handle the output presentation.
     */
    public CreateApplicationInteractor(IApplicationRepository applicationRepository,
                                       IUserProjectsRepository userProjectsRepository,
                                       CreateApplicationOutputBoundary applicationPresenter) {
        this.applicationRepository = applicationRepository;
        this.userProjectsRepository = userProjectsRepository;
        this.applicationPresenter = applicationPresenter;
    }

    /**
     * Creates an application with the provided input data.
     *
     * @param inputData the input data required to create an application.
     */
    @Override
    public void createApplication(CreateApplicationInputData inputData) {
        if (userProjectsRepository.getUserIdsForProject(inputData.getProjectId()).contains(inputData.getSenderUserId())) {
            applicationPresenter.prepareFailView("You already have access to project.");
            return;
        }

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
