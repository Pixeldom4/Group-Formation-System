package usecase.searchprojectbyid;

import entities.ProjectInterface;

/**
 * Interface for the output boundary of the Search Project By ID use case.
 * Defines the method to present the project to the presenter.
 */
public interface SearchProjectByIdOutputBoundary {
    /**
     * Present the project to the presenter.
     *
     * @param project The project to present.
     */
    void presentProject(ProjectInterface project);
}
