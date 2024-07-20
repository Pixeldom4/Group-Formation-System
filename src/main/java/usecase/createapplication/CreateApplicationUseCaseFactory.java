package usecase.createapplication;

import dataaccess.DataAccessConfig;
import dataaccess.IApplicationRepository;
import dataaccess.IUserProjectsRepository;
import viewmodel.SearchPanelViewModel;

public class CreateApplicationUseCaseFactory {
    private final static IApplicationRepository applicationRepository = DataAccessConfig.getApplicationRepository();
    private final static IUserProjectsRepository userProjectsRepository = DataAccessConfig.getUserProjectsRepository();

    private CreateApplicationUseCaseFactory() {
    }

    public static CreateApplicationController createController(SearchPanelViewModel searchPanelViewModel) {
        CreateApplicationOutputBoundary presenter = new CreateApplicationPresenter(searchPanelViewModel);
        CreateApplicationInputBoundary interactor = new CreateApplicationInteractor(applicationRepository, userProjectsRepository, presenter);
        return new CreateApplicationController(interactor);
    }
}
