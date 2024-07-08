package data_access;

import Entities.ProjectInterface;
import api.EmbeddingAPIInterface;
import api.OpenAPIDataEmbed;

import java.util.ArrayList;
import java.util.List;

public class LocalProjectSearchObject implements ProjectSearchInterface {

    private final EmbedDataAccessInterface embedDataAccess = new LocalEmbedDataAccessObject();
    private final EmbeddingAPIInterface embeddingAPI = new OpenAPIDataEmbed();
    private final ProjectDataAccessInterface projectDataAccess = new LocalProjectDataAccessObject();

    public LocalProjectSearchObject() {

    }

    @Override
    public ArrayList<ProjectInterface> searchProjects(String query, int amount) {
        int[] projectIds = embedDataAccess.getClosestProjects(embeddingAPI.getEmbedData(query), amount);
        ArrayList<ProjectInterface> projects = new ArrayList<>();
        for(int id : projectIds) {
            projects.add(projectDataAccess.getProject(id));
        }
        return projects;
    }
}
