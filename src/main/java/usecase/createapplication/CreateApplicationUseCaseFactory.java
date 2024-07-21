package usecase.createapplication;

import dataaccess.DataAccessConfig;
import dataaccess.IApplicationRepository;
import dataaccess.IUserProjectsRepository;
import viewmodel.SearchPanelViewModel;

/**
 * Factory class for creating instances of the CreateApplication use case.
 */
public class CreateApplicationUseCaseFactory {
    private final static IApplicationRepository applicationRepository = DataAccessConfig.getApplicationRepository();
    private final static IUserProjectsRepository userProjectsRepository = DataAccessConfig.getUserProjectsRepository();

    // Private constructor to prevent instantiation
    private CreateApplicationUseCaseFactory() {}

    /**
     * Creates and returns a CreateApplicationController instance.
     *
     * @param searchPanelViewModel the view model to update with creation results.
     * @return a CreateApplicationController instance.
     */
    public static CreateApplicationController createController(SearchPanelViewModel searchPanelViewModel) {
        CreateApplicationOutputBoundary presenter = new CreateApplicationPresenter(searchPanelViewModel);
        CreateApplicationInputBoundary interactor = new CreateApplicationInteractor(applicationRepository, userProjectsRepository, presenter);
        return new CreateApplicationController(interactor);
    }
}
