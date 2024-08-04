package usecase.deleteproject;

import dataaccess.DataAccessConfig;
import dataaccess.ILoginUserDetails;
import dataaccess.IProjectRepository;
import viewmodel.MyProjectsPanelViewModel;

/**
 * Factory class for creating instances of the DeleteProject use case.
 */
public class DeleteProjectUseCaseFactory {
    private static final IProjectRepository projectRepository = DataAccessConfig.getProjectRepository();
    private static final ILoginUserDetails loginUserDetails = DataAccessConfig.getLoginUserDetails();

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
        DeleteProjectInputBoundary deleteProjectInteractor = new DeleteProjectInteractor(deleteProjectPresenter, projectRepository,
                                                                                         loginUserDetails);
        return new DeleteProjectController(deleteProjectInteractor);
    }
}
