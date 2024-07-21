package usecase.searchforproject;

import entities.ProjectInterface;

import java.util.ArrayList;

/**
 * Output boundary interface for searching projects.
 * Defines methods to present the search results.
 */
public interface SearchProjectOutputBoundary {

    /**
     * Sends a list of projects to be presented to the presenter.
     *
     * @param projects the list of projects to be presented.
     */
    void presentProjects(ArrayList<ProjectInterface> projects);
}
