package usecase.manageprojects.deleteproject;

import viewmodel.MyProjectsPanelViewModel;

/**
 * Factory class for creating instances of the DeleteProject use case.
 */
public class DeleteProjectUseCaseFactory {

    // Private constructor to prevent instantiation
    private DeleteProjectUseCaseFactory() {}

    /**
     * Creates a new DeleteProjectController with the given MyProjectsPanelViewModel.
     *
     * @param myProjectsPanelViewModel the MyProjectsPanelViewModel
     * @return a new DeleteProjectController
     */
    public static DeleteProjectController createDeleteProjectController(MyProjectsPanelViewModel myProjectsPanelViewModel) {
        DeleteProjectOutputBoundary deleteProjectPresenter = new DeleteProjectPresenter(myProjectsPanelViewModel);
        DeleteProjectInputBoundary deleteProjectInteractor = new DeleteProjectInteractor(deleteProjectPresenter);
        return new DeleteProjectController(deleteProjectInteractor);
    }
}
