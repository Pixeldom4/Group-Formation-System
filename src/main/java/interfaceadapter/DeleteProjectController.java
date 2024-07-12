package interfaceadapter;

import usecase.deleteproject.DeleteProjectInputBoundary;
import dataaccess.database.ProjectRepository;

public class DeleteProjectController implements DeleteProjectInputBoundary {

    final private ProjectRepository projectData;
    public DeleteProjectController(ProjectRepository projectData){
        this.projectData = projectData;
    }

    @Override
    public void execute(int projectId) {
        System.out.println("deleting");
        this.projectData.deleteProject(projectId);
    }
}
