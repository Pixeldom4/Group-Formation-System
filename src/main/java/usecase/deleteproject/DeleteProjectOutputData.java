package usecase.deleteproject;

public class DeleteProjectOutputData {
    private final int projectId;
    public DeleteProjectOutputData(int projectId){
        this.projectId = projectId;
    }

    public int getProjectId() {
        return projectId;
    }
}
