package usecase.manageprojects.getprojects;

import viewmodel.MyProjectsPanelViewModel;

/**
 * Factory class for creating instances of the GetProjects use case.
 */
public class GetProjectsUseCaseFactory {

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
        GetProjectsInputBoundary getProjectsInteractor = new GetProjectsInteractor(getProjectsPresenter);
        return new GetProjectsController(getProjectsInteractor);
    }
}
