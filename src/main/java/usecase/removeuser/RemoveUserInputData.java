package usecase.removeuser;

public class RemoveUserInputData {
    private final int projectId;
    private final int userId;

    public RemoveUserInputData(int projectId, int userId){
        this.projectId = projectId;
        this.userId = userId;
    }

    public int getProjectId() {
        return projectId;
    }

    public int getUserId() {
        return userId;
    }
}
