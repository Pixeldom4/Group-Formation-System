package usecase.createproject;

import dataaccess.DAOImplementationConfig;
import dataaccess.IProjectRepository;
import viewmodel.AddProjectPanelViewModel;

public class CreateProjectUseCaseFactory {
    private static final IProjectRepository projectRepository = DAOImplementationConfig.getProjectRepository();

    private CreateProjectUseCaseFactory(){}

    /**
     * Creates a new CreateProjectController with the given AddProjectPanelViewModel.
     *
     * @param addProjectPanelViewModel the AddProjectPanelViewModel
     * @return a new CreateProjectController
     */
    public static CreateProjectController createController(AddProjectPanelViewModel addProjectPanelViewModel) {
        CreateProjectOutputBoundary outputBoundary = new CreateProjectPresenter(addProjectPanelViewModel);
        CreateProjectInputBoundary inputBoundary = new CreateProjectInteractor(projectRepository, outputBoundary);
        return new CreateProjectController(inputBoundary);
    }
}
