package usecase.rejectapplication;

public class RejectApplicationInputData {
    private int userId;
    private int projectId;

    public RejectApplicationInputData(int projectId, int userId){
        this.projectId = projectId;
        this.userId = userId;
    }

    public RejectApplicationInputData(int projectId){
        this.projectId = projectId;
    }

    public int getUserId() {
        return userId;
    }

    public int getProjectId() {
        return projectId;
    }
}
