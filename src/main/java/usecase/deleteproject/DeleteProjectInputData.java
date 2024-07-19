package usecase.deleteproject;

public class DeleteProjectInputData {
    private final int projectId;
    public DeleteProjectInputData(int projectId){
        this.projectId = projectId;
    }

    public int getProjectId() {
        return projectId;
    }
}
