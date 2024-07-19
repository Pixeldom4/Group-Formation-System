package usecase.deleteuser;

public class DeleteUserInputData {
    private final int projectId;
    private final int userId;

    public DeleteUserInputData(int projectId, int userId){
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
