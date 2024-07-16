package usecase.searchprojectbyid;

import entities.ProjectInterface;

public interface SearchProjectByIdOutputBoundary {
    /**
     * Present the project to the presenter.
     *
     * @param project The project to present.
     */
    void presentProject(ProjectInterface project);
}
