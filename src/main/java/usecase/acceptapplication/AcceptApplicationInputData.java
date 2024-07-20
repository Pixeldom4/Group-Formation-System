package usecase.acceptapplication;

public class AcceptApplicationInputData {
    private int userId;
    private int projectId;

    public AcceptApplicationInputData(int projectId, int userId){
        this.projectId = projectId;
        this.userId = userId;
    }

    public AcceptApplicationInputData(int projectId){
        this.projectId = projectId;
    }

    public int getUserId() {
        return userId;
    }

    public int getProjectId() {
        return projectId;
    }
}
