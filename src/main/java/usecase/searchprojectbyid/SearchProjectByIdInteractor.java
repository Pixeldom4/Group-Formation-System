package usecase.searchprojectbyid;

import dataaccess.IProjectRepository;
import entities.ProjectInterface;

/**
 * Interactor for the Search Project By ID use case.
 * Implements the SearchProjectByIdInputBoundary interface to handle the input data.
 */
public class SearchProjectByIdInteractor implements SearchProjectByIdInputBoundary {
    private SearchProjectByIdInterface projectDAO = new LocalProjectSearchByIdObject();
    private SearchProjectByIdOutputBoundary presenter;

    /**
     * Creates a new SearchProjectByIdInteractor using the given output boundary.
     *
     * @param presenter The output boundary.
     */
    public SearchProjectByIdInteractor(SearchProjectByIdOutputBoundary presenter) {
        this.presenter = presenter;
    }

    /**
     * Creates a new SearchProjectByIdInteractor using the given output boundary and project repository.
     * Used for testing where files are not stored in the project folder.
     *
     * @param presenter The output boundary.
     * @param projectRepository The project repository to use.
     */
    public SearchProjectByIdInteractor(SearchProjectByIdOutputBoundary presenter, IProjectRepository projectRepository) {
        this.presenter = presenter;
        this.projectDAO = new LocalProjectSearchByIdObject(projectRepository);
    }

    /**
     * Searches for a project by its ID.
     *
     * @param projectId The ID of the project to search for.
     */
    @Override
    public void searchProjectById(int projectId) {
        ProjectInterface project = projectDAO.searchProjectById(projectId);
        presenter.presentProject(project);
    }
}
