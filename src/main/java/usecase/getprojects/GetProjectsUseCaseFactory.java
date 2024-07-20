package usecase.getprojects;

import viewmodel.MyProjectsPanelViewModel;

public class GetProjectsUseCaseFactory {

    private GetProjectsUseCaseFactory() {}

    public static GetProjectsController createGetProjectsController(MyProjectsPanelViewModel myProjectsPanelViewModel) {
        GetProjectsOutputBoundary getProjectsPresenter = new GetProjectsPresenter(myProjectsPanelViewModel);
        GetProjectsInputBoundary getProjectsInteractor = new GetProjectsInteractor(getProjectsPresenter);
        return new GetProjectsController(getProjectsInteractor);
    }
}
