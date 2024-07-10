package usecase.searchforproject;

import entities.ProjectInterface;

import java.util.ArrayList;

public interface SearchProjectOutputBoundary {

    /**
     * Sends a list of projects to be presented to the presenter.
     *
     * @param projects the list of projects to be presented
     */
    void presentProjects(ArrayList<ProjectInterface> projects);
}
