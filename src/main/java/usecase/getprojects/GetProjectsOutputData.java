package usecase.getprojects;

import java.util.HashSet;

/**
 * This is an immutable data structure responsible for storing the output
 * data related to retrieving projects.
 */
public class GetProjectsOutputData {
    private final HashSet<ProjectData> projects;

    /**
     * Constructs a GetProjectsOutputData object with the specified projects data.
     *
     * @param projects the projects data.
     */
    public GetProjectsOutputData(HashSet<ProjectData> projects) {
        this.projects = projects;
    }

    /**
     * Gets the projects data.
     *
     * @return the projects data.
     */
    public HashSet<ProjectData> getData() {
        return this.projects;
    }
}
