package usecase.getprojects;

import dataaccess.DataAccessConfig;
import dataaccess.IProjectRepository;
import dataaccess.IUserProjectsRepository;
import viewmodel.MyProjectsPanelViewModel;

/**
 * Factory class for creating instances of the GetProjects use case.
 */
public class GetProjectsUseCaseFactory {
    private static final IUserProjectsRepository userProjectsRepository = DataAccessConfig.getUserProjectsRepository();
    private static final IProjectRepository projectRepository = DataAccessConfig.getProjectRepository();

    // Private constructor to prevent instantiation
    private GetProjectsUseCaseFactory() {
    }

    /**
     * Creates a new GetProjectsController with the given MyProjectsPanelViewModel.
     *
     * @param myProjectsPanelViewModel the MyProjectsPanelViewModel
     * @return a new GetProjectsController
     */
    public static GetProjectsController createGetProjectsController(MyProjectsPanelViewModel myProjectsPanelViewModel) {
        GetProjectsOutputBoundary getProjectsPresenter = new GetProjectsPresenter(myProjectsPanelViewModel);
        GetProjectsInputBoundary getProjectsInteractor = new GetProjectsInteractor(getProjectsPresenter, userProjectsRepository,
                                                                                   projectRepository);
        return new GetProjectsController(getProjectsInteractor);
    }
}
